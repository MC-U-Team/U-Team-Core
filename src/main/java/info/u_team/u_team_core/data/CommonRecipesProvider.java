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
import net.minecraft.tags.Tag;
import net.minecraft.util.*;

public abstract class CommonRecipesProvider extends CommonProvider {
	
	protected CommonRecipesProvider(DataGenerator generator) {
		super(generator);
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
			final ResourceLocation recipeID = recipe.getID();
			write(cache, recipe.getRecipeJson(), path.resolve(recipeID.getNamespace()).resolve("recipes").resolve(recipe.getID().getPath() + ".json"));
			if (recipe.getAdvancementJson() != null) {
				final Path advancementPath;
				if (vanillaAdvancement) {
					final ResourceLocation advancementID = recipe.getAdvancementID();
					advancementPath = path.resolve(advancementID.getNamespace()).resolve("advancements").resolve(advancementID.getPath() + ".json");
				} else {
					advancementPath = path.resolve(recipeID.getNamespace()).resolve("advancements").resolve("recipes").resolve(recipeID.getPath() + ".json");
				}
				write(cache, recipe.getAdvancementJson(), advancementPath);
			}
		} catch (IOException ex) {
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
	
	@Override
	protected Path resolvePath(Path outputFolder) {
		return outputFolder.resolve("data");
	}
	
	protected InventoryChangeTrigger.Instance hasItem(Tag<Item> tag) {
		return hasItem(ItemPredicate.Builder.create().tag(tag).build());
	}
	
	protected InventoryChangeTrigger.Instance hasItem(IItemProvider item) {
		return hasItem(ItemPredicate.Builder.create().item(item).build());
	}
	
	protected InventoryChangeTrigger.Instance hasItem(ItemPredicate... predicates) {
		return new InventoryChangeTrigger.Instance(IntBound.UNBOUNDED, IntBound.UNBOUNDED, IntBound.UNBOUNDED, predicates);
	}
	
	public static Ingredient getIngredientOfTag(Tag<Item> tag) {
		return TagUtil.getSerializableIngredientOfTag(tag);
	}
	
}
