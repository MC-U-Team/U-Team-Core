package info.u_team.u_team_core.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

import info.u_team.u_team_core.util.TagUtil;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public abstract class CommonRecipesProvider extends CommonProvider {
	
	public CommonRecipesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void run(HashCache cache) throws IOException {
		registerRecipes(recipe -> generateRecipe(cache, recipe, false));
		registerDefaultAdvancementsRecipes(recipe -> generateRecipe(cache, recipe, true));
	}
	
	@Override
	public String getName() {
		return "Recipes";
	}
	
	private void generateRecipe(HashCache cache, FinishedRecipe recipe, boolean vanillaAdvancement) {
		try {
			final var recipeLocation = recipe.getId();
			write(cache, recipe.serializeRecipe(), resolveData(recipeLocation).resolve("recipes").resolve(recipe.getId().getPath() + ".json"));
			if (recipe.serializeAdvancement() != null) {
				final Path advancementPath;
				if (vanillaAdvancement) {
					final var advancementLocation = recipe.getAdvancementId();
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
	
	protected abstract void registerRecipes(Consumer<FinishedRecipe> consumer);
	
	/**
	 * Override this method if you want to add recipes that have the vanilla path for advancements
	 *
	 * @param consumer
	 */
	protected void registerDefaultAdvancementsRecipes(Consumer<FinishedRecipe> consumer) {
	}
	
	protected InventoryChangeTrigger.TriggerInstance hasItem(Tag<Item> tag) {
		return hasItem(ItemPredicate.Builder.item().of(tag).build());
	}
	
	protected InventoryChangeTrigger.TriggerInstance hasItem(ItemLike item) {
		return hasItem(ItemPredicate.Builder.item().of(item).build());
	}
	
	protected InventoryChangeTrigger.TriggerInstance hasItem(ItemPredicate... predicates) {
		return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, Ints.ANY, Ints.ANY, Ints.ANY, predicates);
	}
	
	public static Ingredient getIngredientOfTag(Tag<Item> tag) {
		return TagUtil.getSerializableIngredientOfTag(tag);
	}
	
}
