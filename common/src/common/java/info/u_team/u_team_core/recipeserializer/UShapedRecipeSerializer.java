package info.u_team.u_team_core.recipeserializer;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public abstract class UShapedRecipeSerializer<T extends ShapedRecipe> implements RecipeSerializer<T> {
	
	public static String[] shrink(List<String> list) {
		return ShapedRecipe.shrink(list);
	}
	
	private final Codec<T> codec = ShapedRecipe.Serializer.RawShapedRecipe.CODEC.flatXmap((rawRecipe) -> {
		final String[] shrinked = ShapedRecipe.shrink(rawRecipe.pattern());
		final int width = shrinked[0].length();
		final int height = shrinked.length;
		final NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);
		final Set<String> keys = Sets.newHashSet(rawRecipe.key().keySet());
		
		for (int outer = 0; outer < shrinked.length; ++outer) {
			final String outerKey = shrinked[outer];
			for (int inner = 0; inner < outerKey.length(); ++inner) {
				final String innterKey = outerKey.substring(inner, inner + 1);
				
				final Ingredient ingredient = innterKey.equals(" ") ? Ingredient.EMPTY : rawRecipe.key().get(innterKey);
				if (ingredient == null) {
					return DataResult.error(() -> {
						return "Pattern references symbol '" + innterKey + "' but it's not defined in the key";
					});
				}
				
				keys.remove(innterKey);
				ingredients.set(inner + width * outer, ingredient);
			}
		}
		
		if (!keys.isEmpty()) {
			return DataResult.error(() -> {
				return "Key defines symbols that aren't used in pattern: " + keys;
			});
		} else {
			return DataResult.success(createRecipe(rawRecipe.group(), rawRecipe.category(), width, height, ingredients, rawRecipe.result(), rawRecipe.showNotification()));
		}
	}, recipe -> {
		throw new NotImplementedException("Serializing ShapedRecipe is not implemented yet.");
	});
	
	public Codec<T> codec() {
		return codec;
	}
	
	@Override
	public T fromNetwork(FriendlyByteBuf buffer) {
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
		return createRecipe(group, category, recipeWidth, recipeHeight, ingredients, output, showNotification);
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
		buffer.writeItem(recipe.result);
		buffer.writeBoolean(recipe.showNotification);
	}
	
	protected abstract T createRecipe(String group, CraftingBookCategory category, int recipeWidth, int recipeHeight, NonNullList<Ingredient> ingredients, ItemStack output, boolean showNotification);
	
	protected record ShapedRecipeValues(int width, int height, NonNullList<Ingredient> recipeItems, ItemStack result, String group, CraftingBookCategory category, boolean showNotification) {
		
		public ShapedRecipeValues(ShapedRecipe shapedRecipe) {
			this(shapedRecipe.width, shapedRecipe.height, shapedRecipe.recipeItems, shapedRecipe.result, shapedRecipe.group, shapedRecipe.category, shapedRecipe.showNotification);
		}
	}
	
}
