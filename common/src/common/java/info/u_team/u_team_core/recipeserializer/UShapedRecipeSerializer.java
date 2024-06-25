package info.u_team.u_team_core.recipeserializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

public abstract class UShapedRecipeSerializer<T extends ShapedRecipe> implements RecipeSerializer<T> {
	
	private final MapCodec<T> codec = RecordCodecBuilder.mapCodec(instance -> {
		return instance.group(Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> {
			return recipe.group;
		}), CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(recipe -> {
			return recipe.category;
		}), ShapedRecipePattern.MAP_CODEC.forGetter((recipe) -> {
			return recipe.pattern;
		}), ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> {
			return recipe.result;
		}), Codec.BOOL.optionalFieldOf("show_notification", Boolean.valueOf(true)).forGetter(recipe -> {
			return recipe.showNotification;
		})).apply(instance, this::createRecipe);
	});
	
	private final StreamCodec<RegistryFriendlyByteBuf, T> stream_codec = StreamCodec.of(this::toNetwork, this::fromNetwork);
	
	@Override
	public MapCodec<T> codec() {
		return codec;
	}
	
	@Override
	public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
		return stream_codec;
	}
	
	public T fromNetwork(RegistryFriendlyByteBuf buffer) {
		final String group = buffer.readUtf();
		final CraftingBookCategory category = buffer.readEnum(CraftingBookCategory.class);
		final ShapedRecipePattern pattern = ShapedRecipePattern.STREAM_CODEC.decode(buffer);
		final ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
		final boolean showNotification = buffer.readBoolean();
		return createRecipe(group, category, pattern, result, showNotification);
	}
	
	public void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
		buffer.writeUtf(recipe.group);
		buffer.writeEnum(recipe.category);
		ShapedRecipePattern.STREAM_CODEC.encode(buffer, recipe.pattern);
		ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
		buffer.writeBoolean(recipe.showNotification);
	}
	
	protected abstract T createRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result, boolean showNotification);
	
	protected record ShapedRecipeValues(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result, boolean showNotification) {
		
		public ShapedRecipeValues(ShapedRecipe shapedRecipe) {
			this(shapedRecipe.group, shapedRecipe.category, shapedRecipe.pattern, shapedRecipe.result, shapedRecipe.showNotification);
		}
	}
	
}
