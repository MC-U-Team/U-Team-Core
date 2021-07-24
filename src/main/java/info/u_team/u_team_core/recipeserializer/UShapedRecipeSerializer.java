package info.u_team.u_team_core.recipeserializer;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.NonNullList;

public abstract class UShapedRecipeSerializer<T extends ShapedRecipe> extends URecipeSerializer<T> {
	
	protected static Map<String, Ingredient> deserializeKey(JsonObject json) {
		return ShapedRecipe.keyFromJson(json);
	}
	
	protected static String[] shrink(String... shrink) {
		return ShapedRecipe.shrink(shrink);
	}
	
	protected static String[] patternFromJson(JsonArray json) {
		return ShapedRecipe.patternFromJson(json);
	}
	
	protected static NonNullList<Ingredient> deserializeIngredients(String[] pattern, Map<String, Ingredient> keys, int width, int height) {
		return ShapedRecipe.dissolvePattern(pattern, keys, width, height);
	}
	
}
