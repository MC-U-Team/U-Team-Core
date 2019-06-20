package info.u_team.u_team_core.recipeserializer;

import java.util.Map;

import com.google.gson.*;

import net.minecraft.item.crafting.*;
import net.minecraft.util.*;

public abstract class UShapedRecipeSerializer<T extends ShapedRecipe> extends URecipeSerializer<T> {
	
	public UShapedRecipeSerializer(String name) {
		super(name);
	}
	
	protected static Map<String, Ingredient> deserializeKey(JsonObject json) {
		return ShapedRecipe.deserializeKey(JSONUtils.getJsonObject(json, "key"));
	}
	
	protected static String[] shrink(String... toShrink) {
		return ShapedRecipe.shrink(toShrink);
	}
	
	protected static String[] patternFromJson(JsonArray jsonArr) {
		return ShapedRecipe.patternFromJson(jsonArr);
	}
	
	protected static NonNullList<Ingredient> deserializeIngredients(String[] pattern, Map<String, Ingredient> keys, int patternWidth, int patternHeight) {
		return ShapedRecipe.deserializeIngredients(pattern, keys, patternWidth, patternHeight);
	}
	
}
