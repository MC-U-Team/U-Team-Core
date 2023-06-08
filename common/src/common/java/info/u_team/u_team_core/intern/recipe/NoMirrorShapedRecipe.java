package info.u_team.u_team_core.intern.recipe;

import info.u_team.u_team_core.intern.init.UCoreRecipeSerializers;
import info.u_team.u_team_core.recipeserializer.UShapedRecipeSerializer;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class NoMirrorShapedRecipe extends ShapedRecipe {
	
	public NoMirrorShapedRecipe(ResourceLocation location, String group, CraftingBookCategory category, int recipeWidth, int recipeHeigt, NonNullList<Ingredient> ingredients, ItemStack output, boolean showNotification) {
		super(location, group, category, recipeWidth, recipeHeigt, ingredients, output, showNotification);
	}
	
	@Override
	public boolean matches(CraftingContainer container, Level level) {
		for (int i = 0; i <= container.getWidth() - getWidth(); ++i) {
			for (int j = 0; j <= container.getHeight() - getHeight(); ++j) {
				if (matches(container, i, j, false)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return UCoreRecipeSerializers.NO_MIRROR_SHAPED.get();
	}
	
	public static class Serializer extends UShapedRecipeSerializer<NoMirrorShapedRecipe> {
		
		@Override
		protected NoMirrorShapedRecipe createRecipe(ResourceLocation location, String group, CraftingBookCategory category, int recipeWidth, int recipeHeigt, NonNullList<Ingredient> ingredients, ItemStack output, boolean showNotification) {
			return new NoMirrorShapedRecipe(location, group, category, recipeWidth, recipeHeigt, ingredients, output, showNotification);
		}
	}
}
