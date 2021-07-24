package info.u_team.u_team_core.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

import info.u_team.u_team_core.util.TagUtil;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds.IntBound;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public abstract class CommonRecipesProvider extends CommonProvider {
	
	public CommonRecipesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void run(DirectoryCache cache) throws IOException {
		registerRecipes(recipe -> generateRecipe(cache, recipe, false));
		registerDefaultAdvancementsRecipes(recipe -> generateRecipe(cache, recipe, true));
	}
	
	@Override
	public String getName() {
		return "Recipes";
	}
	
	private void generateRecipe(DirectoryCache cache, IFinishedRecipe recipe, boolean vanillaAdvancement) {
		try {
			final ResourceLocation recipeLocation = recipe.getId();
			write(cache, recipe.serializeRecipe(), resolveData(recipeLocation).resolve("recipes").resolve(recipe.getId().getPath() + ".json"));
			if (recipe.serializeAdvancement() != null) {
				final Path advancementPath;
				if (vanillaAdvancement) {
					final ResourceLocation advancementLocation = recipe.getAdvancementId();
					advancementPath = resolveData(advancementLocation).resolve("advancements").resolve(advancementLocation.getPath() + ".json");
				} else {
					advancementPath = resolveData(recipeLocation).resolve("advancements").resolve("recipes").resolve(recipeLocation.getPath() + ".json");
				}
				write(cache, recipe.serializeAdvancement(), advancementPath);
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
		return hasItem(ItemPredicate.Builder.item().of(tag).build());
	}
	
	protected InventoryChangeTrigger.Instance hasItem(IItemProvider item) {
		return hasItem(ItemPredicate.Builder.item().of(item).build());
	}
	
	protected InventoryChangeTrigger.Instance hasItem(ItemPredicate... predicates) {
		return new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY, IntBound.ANY, IntBound.ANY, IntBound.ANY, predicates);
	}
	
	public static Ingredient getIngredientOfTag(ITag<Item> tag) {
		return TagUtil.getSerializableIngredientOfTag(tag);
	}
	
}
