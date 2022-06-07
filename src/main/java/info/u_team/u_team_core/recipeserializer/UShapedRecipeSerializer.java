package info.u_team.u_team_core.recipeserializer;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public abstract class UShapedRecipeSerializer<T extends ShapedRecipe> implements RecipeSerializer<T> {
	
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
