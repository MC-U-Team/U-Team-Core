package info.u_team.u_team_core.intern.recipe;

import com.google.gson.JsonObject;

import info.u_team.u_team_core.intern.init.UCoreRecipeSerializers;
import info.u_team.u_team_core.recipeserializer.UShapedRecipeSerializer;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class NoMirrorShapedRecipe extends ShapedRecipe {
	
	public NoMirrorShapedRecipe(ResourceLocation location, String group, int recipeWidth, int recipeHeigt, NonNullList<Ingredient> ingredients, ItemStack output) {
		super(location, group, recipeWidth, recipeHeigt, ingredients, output);
	}
	
	@Override
	public boolean matches(CraftingInventory inventory, World world) {
		for (int i = 0; i <= inventory.getWidth() - getWidth(); ++i) {
			for (int j = 0; j <= inventory.getHeight() - getHeight(); ++j) {
				if (checkMatch(inventory, i, j, false)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public IRecipeSerializer<?> getSerializer() {
		return UCoreRecipeSerializers.NO_MIRROR_SHAPED.get();
	}
	
	public static class Serializer extends UShapedRecipeSerializer<NoMirrorShapedRecipe> {
		
		@Override
		public NoMirrorShapedRecipe read(ResourceLocation location, JsonObject json) {
			final String[] pattern = patternFromJson(JSONUtils.getJsonArray(json, "pattern"));
			final int recipeWidth = pattern[0].length();
			final int recipeHeight = pattern.length;
			final String group = JSONUtils.getString(json, "group", "");
			final NonNullList<Ingredient> ingredients = deserializeIngredients(pattern, deserializeKey(JSONUtils.getJsonObject(json, "key")), recipeWidth, recipeHeight);
			final ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
			return new NoMirrorShapedRecipe(location, group, recipeWidth, recipeHeight, ingredients, output);
		}
		
		@Override
		public NoMirrorShapedRecipe read(ResourceLocation location, PacketBuffer buffer) {
			final int recipeWidth = buffer.readVarInt();
			final int recipeHeight = buffer.readVarInt();
			final String group = buffer.readString(32767);
			final NonNullList<Ingredient> ingredients = NonNullList.withSize(recipeWidth * recipeHeight, Ingredient.EMPTY);
			for (int k = 0; k < ingredients.size(); ++k) {
				ingredients.set(k, Ingredient.read(buffer));
			}
			final ItemStack output = buffer.readItemStack();
			return new NoMirrorShapedRecipe(location, group, recipeWidth, recipeHeight, ingredients, output);
		}
		
		@Override
		public void write(PacketBuffer buffer, NoMirrorShapedRecipe recipe) {
			buffer.writeVarInt(recipe.getRecipeWidth());
			buffer.writeVarInt(recipe.getRecipeHeight());
			buffer.writeString(recipe.getGroup());
			for (final Ingredient ingredient : recipe.getIngredients()) {
				ingredient.write(buffer);
			}
			buffer.writeItemStack(recipe.getRecipeOutput());
		}
	}
}
