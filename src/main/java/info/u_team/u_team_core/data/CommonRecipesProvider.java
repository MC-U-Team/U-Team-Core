package info.u_team.u_team_core.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

import info.u_team.u_team_core.util.TagUtil;
import net.minecraft.advancements.criterion.*;
import net.minecraft.advancements.criterion.MinMaxBounds.IntBound;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.*;

public abstract class CommonRecipesProvider extends CommonProvider {
	
	public CommonRecipesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void act(DirectoryCache cache) throws IOException {
		registerRecipes(recipe -> generateRecipe(cache, recipe, false));
		registerDefaultAdvancementsRecipes(recipe -> generateRecipe(cache, recipe, true));
	}
	
	@Override
	public String getName() {
		return "Recipes";
	}
	
	private void generateRecipe(DirectoryCache cache, IFinishedRecipe recipe, boolean vanillaAdvancement) {
		try {
			final ResourceLocation recipeLocation = recipe.getID();
			write(cache, recipe.getRecipeJson(), resolveData(recipeLocation).resolve("recipes").resolve(recipe.getID().getPath() + ".json"));
			if (recipe.getAdvancementJson() != null) {
				final Path advancementPath;
				if (vanillaAdvancement) {
					final ResourceLocation advancementLocation = recipe.getAdvancementID();
					advancementPath = resolveData(advancementLocation).resolve("advancements").resolve(advancementLocation.getPath() + ".json");
				} else {
					advancementPath = resolveData(recipeLocation).resolve("advancements").resolve("recipes").resolve(recipeLocation.getPath() + ".json");
				}
				write(cache, recipe.getAdvancementJson(), advancementPath);
			}
		} catch (final IOException ex) {
			LOGGER.error(marker, "Could not write data.", ex);
		}
	}
	
	protected abstract void registerRecipes(Consumer<IFinishedRecipe> consumer);
	
	/**
	 * Override this method if you want to add recipes that have the vanilla path for advancements
	 * 
	 * @param consumer
	 */
	protected void registerDefaultAdvancementsRecipes(Consumer<IFinishedRecipe> consumer) {
	}
	
	protected InventoryChangeTrigger.Instance hasItem(ITag<Item> tag) {
		return hasItem(ItemPredicate.Builder.create().tag(tag).build());
	}
	
	protected InventoryChangeTrigger.Instance hasItem(IItemProvider item) {
		return hasItem(ItemPredicate.Builder.create().item(item).build());
	}
	
	protected InventoryChangeTrigger.Instance hasItem(ItemPredicate... predicates) {
		return new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, IntBound.UNBOUNDED, IntBound.UNBOUNDED, IntBound.UNBOUNDED, predicates);
	}
	
	public static Ingredient getIngredientOfTag(ITag<Item> tag) {
		return TagUtil.getSerializableIngredientOfTag(tag);
	}
	
}
