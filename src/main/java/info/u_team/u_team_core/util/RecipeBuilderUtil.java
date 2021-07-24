package info.u_team.u_team_core.util;

import com.google.gson.JsonObject;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.resources.ResourceLocation;

public class RecipeBuilderUtil {
	
	public static FinishedRecipe getRecipeWithSerializer(FinishedRecipe recipe, RecipeSerializer<?> serializer) {
		return new FinishedRecipe() {
			
			@Override
			public void serializeRecipeData(JsonObject json) {
				recipe.serializeRecipeData(json);
			}
			
			@Override
			public RecipeSerializer<?> getType() {
				return serializer;
			}
			
			@Override
			public ResourceLocation getId() {
				return recipe.getId();
			}
			
			@Override
			public JsonObject serializeAdvancement() {
				return recipe.serializeAdvancement();
			}
			
			@Override
			public ResourceLocation getAdvancementId() {
				return recipe.getAdvancementId();
			}
		};
	}
	
}
