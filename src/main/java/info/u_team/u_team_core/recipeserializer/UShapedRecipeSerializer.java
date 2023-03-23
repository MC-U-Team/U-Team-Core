package info.u_team.u_team_core.recipeserializer;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public abstract class UShapedRecipeSerializer<T extends ShapedRecipe> implements RecipeSerializer<T> {
	
	public static NonNullList<Ingredient> dissolvePattern(String[] pattern, Map<String, Ingredient> keys, int width, int height) {
		return ShapedRecipe.dissolvePattern(pattern, keys, width, height);
	}
	
	public static String[] shrink(String... shrink) {
		return ShapedRecipe.shrink(shrink);
	}
	
	public static String[] patternFromJson(JsonArray json) {
		return ShapedRecipe.patternFromJson(json);
	}
	
	public static Map<String, Ingredient> keyFromJson(JsonObject json) {
		return ShapedRecipe.keyFromJson(json);
	}
	
	public static ItemStack itemStackFromJson(JsonObject json) {
		return ShapedRecipe.itemStackFromJson(json);
	}
	
	public static Item itemFromJson(JsonObject json) {
		return ShapedRecipe.itemFromJson(json);
	}
	
	@Override
	public T fromJson(ResourceLocation location, JsonObject json) {
		final String[] pattern = patternFromJson(GsonHelper.getAsJsonArray(json, "pattern"));
		final int recipeWidth = pattern[0].length();
		final int recipeHeight = pattern.length;
		final String group = GsonHelper.getAsString(json, "group", "");
		@SuppressWarnings("deprecation")
		final CraftingBookCategory category = CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", null), CraftingBookCategory.MISC);
		final NonNullList<Ingredient> ingredients = dissolvePattern(pattern, keyFromJson(GsonHelper.getAsJsonObject(json, "key")), recipeWidth, recipeHeight);
		final ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
		final boolean showNotification = GsonHelper.getAsBoolean(json, "show_notification", true);
		return createRecipe(location, group, category, recipeWidth, recipeHeight, ingredients, output, showNotification);
	}
	
	@Override
	public T fromNetwork(ResourceLocation location, FriendlyByteBuf buffer) {
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
		return createRecipe(location, group, category, recipeWidth, recipeHeight, ingredients, output, showNotification);
	}
	
	@Override
	public void toNetwork(FriendlyByteBuf buffer, T recipe) {
		buffer.writeVarInt(recipe.width);
		buffer.writeVarInt(recipe.height);
		buffer.writeUtf(recipe.group);
		buffer.writeEnum(recipe.category);
		for (final Ingredient ingredient : recipe.recipeItems) {
			ingredient.toNetwork(buffer);
		}
		buffer.writeBoolean(recipe.showNotification);
		buffer.writeItem(recipe.result);
	}
	
	protected abstract T createRecipe(ResourceLocation location, String group, CraftingBookCategory category, int recipeWidth, int recipeHeight, NonNullList<Ingredient> ingredients, ItemStack output, boolean showNotification);
	
	protected record ShapedRecipeValues(int width, int height, NonNullList<Ingredient> recipeItems, ItemStack result, ResourceLocation id, String group, CraftingBookCategory category, boolean showNotification) {
		
		public ShapedRecipeValues(ShapedRecipe shapedRecipe) {
			this(shapedRecipe.width, shapedRecipe.height, shapedRecipe.recipeItems, shapedRecipe.result, shapedRecipe.id, shapedRecipe.group, shapedRecipe.category, shapedRecipe.showNotification);
		}
	}
	
}
