package info.u_team.u_team_core.util;

import com.google.gson.JsonObject;

import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

public class RecipeBuilderUtil {
	
	public static IFinishedRecipe getRecipeWithSerializer(IFinishedRecipe recipe, IRecipeSerializer<?> serializer) {
		return new IFinishedRecipe() {
			
			@Override
			public void serializeRecipeData(JsonObject json) {
				recipe.serializeRecipeData(json);
			}
			
			@Override
			public IRecipeSerializer<?> getType() {
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
