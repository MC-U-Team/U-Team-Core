package info.u_team.u_team_core.intern.recipe;

import com.google.gson.JsonObject;

import info.u_team.u_team_core.intern.init.UCoreRecipeSerializers;
import info.u_team.u_team_core.recipeserializer.UShapedRecipeSerializer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class NoMirrorShapedRecipe extends ShapedRecipe {
	
	public NoMirrorShapedRecipe(ResourceLocation location, String group, CraftingBookCategory category, int recipeWidth, int recipeHeigt, NonNullList<Ingredient> ingredients, ItemStack output, boolean showNotification) {
		super(location, group, category, recipeWidth, recipeHeigt, ingredients, output, showNotification);
	}
	
	@Override
	public boolean matches(CraftingContainer container, Level level) {
		for (int i = 0; i <= container.getWidth() - getWidth(); ++i) {
			for (int j = 0; j <= container.getHeight() - getHeight(); ++j) {
				if (matches(container, i, j, false)) {
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
			@SuppressWarnings("deprecation")
			final CraftingBookCategory category = CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", null), CraftingBookCategory.MISC);
			final NonNullList<Ingredient> ingredients = deserializeIngredients(pattern, deserializeKey(GsonHelper.getAsJsonObject(json, "key")), recipeWidth, recipeHeight);
			final ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
			final boolean showNotification = GsonHelper.getAsBoolean(json, "show_notification", true);
			return new NoMirrorShapedRecipe(location, group, category, recipeWidth, recipeHeight, ingredients, output, showNotification);
		}
		
		@Override
		public NoMirrorShapedRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf buffer) {
			final int recipeWidth = buffer.readVarInt();
			final int recipeHeight = buffer.readVarInt();
			final String group = buffer.readUtf();
			final CraftingBookCategory category = buffer.readEnum(CraftingBookCategory.class);
			final NonNullList<Ingredient> ingredients = NonNullList.withSize(recipeWidth * recipeHeight, Ingredient.EMPTY);
			for (int k = 0; k < ingredients.size(); ++k) {
				ingredients.set(k, Ingredient.fromNetwork(buffer));
			}
			final ItemStack output = buffer.readItem();
			final boolean showNotification = buffer.readBoolean();
			return new NoMirrorShapedRecipe(location, group, category, recipeWidth, recipeHeight, ingredients, output, showNotification);
		}
		
		@Override
		public void toNetwork(FriendlyByteBuf buffer, NoMirrorShapedRecipe recipe) {
			buffer.writeVarInt(recipe.width);
			buffer.writeVarInt(recipe.height);
			buffer.writeUtf(recipe.group);
			buffer.writeEnum(recipe.category);
			for (final Ingredient ingredient : recipe.recipeItems) {
				ingredient.toNetwork(buffer);
			}
			buffer.writeItem(recipe.result);
		}
	}
}
