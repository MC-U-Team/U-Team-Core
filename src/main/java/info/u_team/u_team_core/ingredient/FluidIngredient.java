package info.u_team.u_team_core.ingredient;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidIngredient implements Predicate<FluidStack> {
	
	public static final FluidIngredient EMPTY = new FluidIngredient(0, Stream.empty());
	
	private final IFluidList[] acceptedFluids;
	private final FluidStack[] matchingFluids;
	private final int amount;
	
	public static FluidIngredient fromFluids(int amount, Fluid... fluids) {
		return new FluidIngredient(amount, Arrays.stream(fluids).map((fluid) -> new SingleFluidList(new FluidStack(fluid, 1000))));
	}
	
	public static FluidIngredient fromStacks(int amount, FluidStack... stacks) {
		return new FluidIngredient(amount, Arrays.stream(stacks).map((stack) -> new SingleFluidList(stack)));
	}
	
	public static FluidIngredient fromTag(int amount, TagKey<Fluid> tag) {
		return new FluidIngredient(amount, Stream.of(new TagList(tag)));
	}
	
	protected FluidIngredient(int amount, Stream<? extends IFluidList> stream) {
		this.amount = amount;
		acceptedFluids = stream.filter(list -> !list.getStacks().stream().allMatch(FluidStack::isEmpty)).toArray(IFluidList[]::new);
		matchingFluids = Arrays.stream(acceptedFluids).flatMap(fluidList -> fluidList.getStacks().stream()).distinct().toArray(FluidStack[]::new);
	}
	
	@Override
	public boolean test(FluidStack stack) {
		if (stack == null) {
			return false;
		} else if (matchingFluids.length == 0) {
			return stack.isEmpty();
		} else {
			for (final FluidStack fluidStack : matchingFluids) {
				if (fluidStack.getFluid() == stack.getFluid()) {
					return stack.getAmount() >= amount;
				}
			}
			return false;
		}
	}
	
	public int getAmount() {
		return amount;
	}
	
	// Network write
	public void write(FriendlyByteBuf buffer) {
		buffer.writeInt(amount);
		buffer.writeVarInt(matchingFluids.length);
		
		for (final FluidStack stack : matchingFluids) {
			buffer.writeFluidStack(stack);
		}
	}
	
	// Network read
	public static FluidIngredient read(FriendlyByteBuf buffer) {
		final int amount = buffer.readInt();
		final int length = buffer.readVarInt();
		
		return new FluidIngredient(amount, Stream.generate(() -> new SingleFluidList(buffer.readFluidStack())).limit(length));
	}
	
	// Serialize
	public JsonElement serialize() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("amount", amount);
		
		if (acceptedFluids.length == 1) {
			jsonObject.add("fluids", acceptedFluids[0].serialize());
		} else {
			final JsonArray jsonArray = new JsonArray();
			
			for (final IFluidList list : acceptedFluids) {
				jsonArray.add(list.serialize());
			}
			jsonObject.add("fluids", jsonArray);
		}
		return jsonObject;
	}
	
	// Deserialize
	
	public static FluidIngredient deserialize(JsonElement jsonElement) {
		if (jsonElement == null || !jsonElement.isJsonObject()) {
			throw new JsonSyntaxException("Fluid ingredient must be a json object");
		}
		
		final JsonObject jsonObject = jsonElement.getAsJsonObject();
		
		if (!jsonObject.has("amount") || !jsonObject.has("fluids")) {
			throw new JsonSyntaxException("Expected amount and fluids");
		}
		
		final int amount = GsonHelper.getAsInt(jsonObject, "amount");
		final JsonElement ingredientJsonElement = jsonObject.get("fluids");
		
		if (ingredientJsonElement.isJsonObject()) {
			return new FluidIngredient(amount, Stream.of(deserializeFluidList(ingredientJsonElement.getAsJsonObject())));
		} else if (ingredientJsonElement.isJsonArray()) {
			final JsonArray jsonArray = ingredientJsonElement.getAsJsonArray();
			if (jsonArray.size() == 0) {
				throw new JsonSyntaxException("Fluid array cannot be empty, at least one fluid must be defined");
			} else {
				return new FluidIngredient(amount, StreamSupport.stream(jsonArray.spliterator(), false).map((jsonArrayElement) -> {
					return deserializeFluidList(GsonHelper.convertToJsonObject(jsonArrayElement, "fluid"));
				}));
			}
		} else {
			throw new JsonSyntaxException("Expected fluid to be object or array of objects");
		}
	}
	
	protected static IFluidList deserializeFluidList(JsonObject jsonObject) {
		if (jsonObject.has("fluid") && jsonObject.has("tag")) {
			throw new JsonParseException("An ingredient entry is either a tag or a fluid, not both");
		} else if (jsonObject.has("fluid")) {
			final ResourceLocation key = new ResourceLocation(GsonHelper.getAsString(jsonObject, "fluid"));
			final Fluid fluid = ForgeRegistries.FLUIDS.getValue(key);
			if (fluid == null) {
				throw new JsonSyntaxException("Unknown fluid '" + key + "'");
			}
			return new SingleFluidList(new FluidStack(fluid, 1000));
		} else if (jsonObject.has("tag")) {
			final ResourceLocation key = new ResourceLocation(GsonHelper.getAsString(jsonObject, "tag"));
			final TagKey<Fluid> tag = TagKey.create(Registry.FLUID_REGISTRY, key);
			return new TagList(tag);
		} else {
			throw new JsonParseException("An ingredient entry needs either a tag or a fluid");
		}
	}
	
	public interface IFluidList {
		
		Collection<FluidStack> getStacks();
		
		JsonObject serialize();
	}
	
	public static class SingleFluidList implements IFluidList {
		
		private final FluidStack stack;
		
		public SingleFluidList(FluidStack stack) {
			this.stack = stack;
		}
		
		@Override
		public Collection<FluidStack> getStacks() {
			return Collections.singleton(stack);
		}
		
		@Override
		public JsonObject serialize() {
			final JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("fluid", ForgeRegistries.FLUIDS.getKey(stack.getFluid()).toString());
			return jsonObject;
		}
	}
	
	public static class TagList implements IFluidList {
		
		private final TagKey<Fluid> tag;
		
		public TagList(TagKey<Fluid> tag) {
			this.tag = tag;
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public Collection<FluidStack> getStacks() {
			final List<FluidStack> list = Lists.newArrayList();
			
			for (final Holder<Fluid> holder : Registry.FLUID.getTagOrEmpty(tag)) {
				list.add(new FluidStack(holder.value(), 1000));
			}
			
			if (list.size() == 0) {
				list.add(new FluidStack(Fluids.LAVA, Integer.MAX_VALUE)); // TODO
			}
			return list;
		}
		
		@Override
		public JsonObject serialize() {
			final JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("tag", tag.location().toString());
			return jsonObject;
		}
	}
}
