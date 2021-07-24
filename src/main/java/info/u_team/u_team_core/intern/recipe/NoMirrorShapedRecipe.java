package info.u_team.u_team_core.intern.recipe;

import com.google.gson.JsonObject;

import info.u_team.u_team_core.intern.init.UCoreRecipeSerializers;
import info.u_team.u_team_core.recipeserializer.UShapedRecipeSerializer;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class NoMirrorShapedRecipe extends ShapedRecipe {
	
	public NoMirrorShapedRecipe(ResourceLocation location, String group, int recipeWidth, int recipeHeigt, NonNullList<Ingredient> ingredients, ItemStack output) {
		super(location, group, recipeWidth, recipeHeigt, ingredients, output);
	}
	
	@Override
	public boolean matches(CraftingContainer inventory, Level world) {
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
	public RecipeSerializer<?> getSerializer() {
		return UCoreRecipeSerializers.NO_MIRROR_SHAPED.get();
	}
	
	public static class Serializer extends UShapedRecipeSerializer<NoMirrorShapedRecipe> {
		
		@Override
		public NoMirrorShapedRecipe fromJson(ResourceLocation location, JsonObject json) {
			final String[] pattern = patternFromJson(GsonHelper.getAsJsonArray(json, "pattern"));
			final int recipeWidth = pattern[0].length();
			final int recipeHeight = pattern.length;
			final String group = GsonHelper.getAsString(json, "group", "");
			final NonNullList<Ingredient> ingredients = deserializeIngredients(pattern, deserializeKey(GsonHelper.getAsJsonObject(json, "key")), recipeWidth, recipeHeight);
			final ItemStack output = ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(json, "result"));
			return new NoMirrorShapedRecipe(location, group, recipeWidth, recipeHeight, ingredients, output);
		}
		
		@Override
		public NoMirrorShapedRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf buffer) {
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
		public void toNetwork(FriendlyByteBuf buffer, NoMirrorShapedRecipe recipe) {
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
