package info.u_team.u_team_core.recipeserializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

public abstract class UShapedRecipeSerializer<T extends ShapedRecipe> implements RecipeSerializer<T> {
	
	private final Codec<T> codec = RecordCodecBuilder.create(instance -> {
		return instance.group(ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> {
			return recipe.group;
		}), CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(recipe -> {
			return recipe.category;
		}), ShapedRecipePattern.MAP_CODEC.forGetter((recipe) -> {
			return recipe.pattern;
		}), ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(recipe -> {
			return recipe.result;
		}), ExtraCodecs.strictOptionalField(Codec.BOOL, "show_notification", true).forGetter(recipe -> {
			return recipe.showNotification;
		})).apply(instance, this::createRecipe);
	});
	
	public Codec<T> codec() {
		return codec;
	}
	
	@Override
	public T fromNetwork(FriendlyByteBuf buffer) {
		final String group = buffer.readUtf();
		final CraftingBookCategory category = buffer.readEnum(CraftingBookCategory.class);
		final ShapedRecipePattern pattern = ShapedRecipePattern.fromNetwork(buffer);
		final ItemStack result = buffer.readItem();
		final boolean showNotification = buffer.readBoolean();
		return createRecipe(group, category, pattern, result, showNotification);
	}
	
	@Override
	public void toNetwork(FriendlyByteBuf buffer, T recipe) {
		buffer.writeUtf(recipe.group);
		buffer.writeEnum(recipe.category);
		recipe.pattern.toNetwork(buffer);
		buffer.writeItem(recipe.result);
		buffer.writeBoolean(recipe.showNotification);
	}
	
	protected abstract T createRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result, boolean showNotification);
	
	protected record ShapedRecipeValues(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result, boolean showNotification) {
		
		public ShapedRecipeValues(ShapedRecipe shapedRecipe) {
			this(shapedRecipe.group, shapedRecipe.category, shapedRecipe.pattern, shapedRecipe.result, shapedRecipe.showNotification);
		}
	}
	
}
