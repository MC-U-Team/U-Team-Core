package info.u_team.u_team_core.util;

import com.google.gson.JsonElement;

import info.u_team.u_team_core.ingredient.FluidIngredient;

public class SerializeUtil {
	
	public static JsonElement serializeFluidIngredient(FluidIngredient ingredient) {
		return ingredient.serialize();
	}
	
	public static FluidIngredient deserializeFluidIngredient(JsonElement json) {
		return FluidIngredient.deserialize(json);
	}
	
}
