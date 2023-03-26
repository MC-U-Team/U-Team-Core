package info.u_team.u_team_core.ingredient;

import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;

public class ItemIngredient extends Ingredient {
	
	private final int amount;
	
	public static ItemIngredient fromItems(int amount, ItemLike... items) {
		return new ItemIngredient(amount, Arrays.stream(items).map((item) -> new ItemValue(new ItemStack(item))));
	}
	
	public static ItemIngredient fromStacks(int amount, ItemStack... stacks) {
		return new ItemIngredient(amount, Arrays.stream(stacks).map((stack) -> new ItemValue(stack)));
	}
	
	public static ItemIngredient fromTag(int amount, TagKey<Item> tag) {
		return new ItemIngredient(amount, Stream.of(new Ingredient.TagValue(tag)));
	}
	
	protected ItemIngredient(int amount, Stream<? extends Value> stream) {
		super(stream);
		this.amount = amount;
	}
	
	@Override
	public boolean test(ItemStack stack) {
		if (stack == null) {
			return false;
		} else if (values.length == 0) {
			return stack.isEmpty();
		} else {
			for (final ItemStack itemstack : itemStacks) {
				if (itemstack.getItem() == stack.getItem()) {
					return stack.getCount() >= amount;
				}
			}
			return false;
		}
	}
	
	public int getAmount() {
		return amount;
	}
	
	@Override
	public IIngredientSerializer<? extends Ingredient> getSerializer() {
		return Serializer.INSTANCE;
	}
	
	@Override
	public JsonElement toJson() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("type", CraftingHelper.getID(Serializer.INSTANCE).toString());
		jsonObject.addProperty("amount", amount);
		jsonObject.add("items", super.toJson());
		return jsonObject;
	}
	
	public static class Serializer implements IIngredientSerializer<ItemIngredient> {
		
		public static final Serializer INSTANCE = new Serializer();
		
		@Override
		public ItemIngredient parse(JsonObject jsonObject) {
			if (!jsonObject.has("amount") || !jsonObject.has("items")) {
				throw new JsonSyntaxException("Expected amount and items");
			}
			
			final int amount = GsonHelper.getAsInt(jsonObject, "amount");
			final JsonElement ingredientJsonElement = jsonObject.get("items");
			
			if (ingredientJsonElement.isJsonObject()) {
				return new ItemIngredient(amount, Stream.of(valueFromJson(ingredientJsonElement.getAsJsonObject())));
			} else if (ingredientJsonElement.isJsonArray()) {
				final JsonArray jsonArray = ingredientJsonElement.getAsJsonArray();
				if (jsonArray.size() == 0) {
					throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
				} else {
					return new ItemIngredient(amount, StreamSupport.stream(jsonArray.spliterator(), false).map((jsonArrayElement) -> {
						return valueFromJson(GsonHelper.convertToJsonObject(jsonArrayElement, "item"));
					}));
				}
			} else {
				throw new JsonSyntaxException("Expected item to be object or array of objects");
			}
		}
		
		@Override
		public ItemIngredient parse(FriendlyByteBuf buffer) {
			final int amount = buffer.readInt();
			final int length = buffer.readVarInt();
			
			return new ItemIngredient(amount, Stream.generate(() -> new ItemValue(buffer.readItem())).limit(length));
		}
		
		@Override
		public void write(FriendlyByteBuf buffer, ItemIngredient ingredient) {
			final ItemStack[] items = ingredient.getItems();
			buffer.writeInt(ingredient.amount);
			buffer.writeVarInt(items.length);
			
			for (final ItemStack stack : items) {
				buffer.writeItem(stack);
			}
		}
	}
}
