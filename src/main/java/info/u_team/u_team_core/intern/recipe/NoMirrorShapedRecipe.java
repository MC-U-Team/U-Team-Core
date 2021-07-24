package info.u_team.u_team_core.intern.recipe;

import com.google.gson.JsonObject;

import info.u_team.u_team_core.intern.init.UCoreRecipeSerializers;
import info.u_team.u_team_core.recipeserializer.UShapedRecipeSerializer;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class NoMirrorShapedRecipe extends ShapedRecipe {
	
	public NoMirrorShapedRecipe(ResourceLocation location, String group, int recipeWidth, int recipeHeigt, NonNullList<Ingredient> ingredients, ItemStack output) {
		super(location, group, recipeWidth, recipeHeigt, ingredients, output);
	}
	
	@Override
	public boolean matches(CraftingInventory inventory, World world) {
		for (int i = 0; i <= inventory.getWidth() - getWidth(); ++i) {
			for (int j = 0; j <= inventory.getHeight() - getHeight(); ++j) {
				if (matches(inventory, i, j, false)) {
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
		public NoMirrorShapedRecipe fromJson(ResourceLocation location, JsonObject json) {
			final String[] pattern = patternFromJson(JSONUtils.getAsJsonArray(json, "pattern"));
			final int recipeWidth = pattern[0].length();
			final int recipeHeight = pattern.length;
			final String group = JSONUtils.getAsString(json, "group", "");
			final NonNullList<Ingredient> ingredients = deserializeIngredients(pattern, deserializeKey(JSONUtils.getAsJsonObject(json, "key")), recipeWidth, recipeHeight);
			final ItemStack output = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
			return new NoMirrorShapedRecipe(location, group, recipeWidth, recipeHeight, ingredients, output);
		}
		
		@Override
		public NoMirrorShapedRecipe fromNetwork(ResourceLocation location, PacketBuffer buffer) {
			final int recipeWidth = buffer.readVarInt();
			final int recipeHeight = buffer.readVarInt();
			final String group = buffer.readUtf(32767);
			final NonNullList<Ingredient> ingredients = NonNullList.withSize(recipeWidth * recipeHeight, Ingredient.EMPTY);
			for (int k = 0; k < ingredients.size(); ++k) {
				ingredients.set(k, Ingredient.fromNetwork(buffer));
			}
			final ItemStack output = buffer.readItem();
			return new NoMirrorShapedRecipe(location, group, recipeWidth, recipeHeight, ingredients, output);
		}
		
		@Override
		public void toNetwork(PacketBuffer buffer, NoMirrorShapedRecipe recipe) {
			buffer.writeVarInt(recipe.getRecipeWidth());
			buffer.writeVarInt(recipe.getRecipeHeight());
			buffer.writeUtf(recipe.getGroup());
			for (final Ingredient ingredient : recipe.getIngredients()) {
				ingredient.toNetwork(buffer);
			}
			buffer.writeItem(recipe.getResultItem());
		}
	}
}
