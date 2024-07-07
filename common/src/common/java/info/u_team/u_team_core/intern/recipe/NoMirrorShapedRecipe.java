package info.u_team.u_team_core.intern.recipe;

import info.u_team.u_team_core.intern.init.UCoreRecipeSerializers;
import info.u_team.u_team_core.recipeserializer.UShapedRecipeSerializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;

public class NoMirrorShapedRecipe extends ShapedRecipe {
	
	public NoMirrorShapedRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result, boolean showNotification) {
		super(group, category, pattern, result, showNotification);
	}
	
	@Override
	public boolean matches(CraftingInput input, Level level) {
		if (input.ingredientCount() != pattern.ingredientCount) {
			return false;
		} else {
			if (input.width() == pattern.width() && input.height() == pattern.height()) {
				if (pattern.matches(input, false)) {
					return true;
				}
			}
			return false;
		}
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return UCoreRecipeSerializers.NO_MIRROR_SHAPED.get();
	}
	
	public static class Serializer extends UShapedRecipeSerializer<NoMirrorShapedRecipe> {
		
		@Override
		protected NoMirrorShapedRecipe createRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result, boolean showNotification) {
			return new NoMirrorShapedRecipe(group, category, pattern, result, showNotification);
		}
	}
}
