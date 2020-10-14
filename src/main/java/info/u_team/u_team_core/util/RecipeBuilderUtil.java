package info.u_team.u_team_core.util;

import com.google.gson.JsonObject;

import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

public class RecipeBuilderUtil {
	
	public static IFinishedRecipe getRecipeWithSerializer(IFinishedRecipe recipe, IRecipeSerializer<?> serializer) {
		return new IFinishedRecipe() {
			
			@Override
			public void serialize(JsonObject json) {
				recipe.serialize(json);
			}
			
			@Override
			public IRecipeSerializer<?> getSerializer() {
				return serializer;
			}
			
			@Override
			public ResourceLocation getID() {
				return recipe.getID();
			}
			
			@Override
			public JsonObject getAdvancementJson() {
				return recipe.getAdvancementJson();
			}
			
			@Override
			public ResourceLocation getAdvancementID() {
				return recipe.getAdvancementID();
			}
		};
	}
	
}