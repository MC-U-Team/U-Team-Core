package info.u_team.u_team_core.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput.PathProvider;
import net.minecraft.data.PackOutput.Target;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public abstract class CommonRecipeProvider implements DataProvider, CommonDataProvider<Consumer<FinishedRecipe>> {
	
	private final GenerationData generationData;
	
	private final PathProvider recipePathProvider;
	private final PathProvider advancementPathProvider;
	
	public CommonRecipeProvider(GenerationData generationData) {
		this.generationData = generationData;
		
		recipePathProvider = generationData.output().createPathProvider(Target.DATA_PACK, "recipes");
		advancementPathProvider = generationData.output().createPathProvider(Target.DATA_PACK, "advancements");
	}
	
	@Override
	public GenerationData getGenerationData() {
		return generationData;
	}
	
	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		final Set<ResourceLocation> duplicates = Sets.newHashSet();
		final List<CompletableFuture<?>> futures = new ArrayList<>();
		register(recipe -> generateRecipe(cache, recipe, duplicates, false, futures));
		registerVanilla(recipe -> generateRecipe(cache, recipe, duplicates, true, futures));
		return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
	}
	
	@Override
	public String getName() {
		return "Recipe";
	}
	
	public void registerVanilla(Consumer<FinishedRecipe> consumer) {
	}
	
	private void generateRecipe(CachedOutput cache, FinishedRecipe recipe, Set<ResourceLocation> duplicates, boolean vanillaAdvancements, List<CompletableFuture<?>> futures) {
		final ResourceLocation recipeLocation = recipe.getId();
		
		if (!duplicates.add(recipeLocation)) {
			throw new IllegalStateException("Duplicate recipe " + recipeLocation);
		}
		
		futures.add(CommonDataProvider.saveData(cache, recipe.serializeRecipe(), recipePathProvider.json(recipeLocation)));
		
		final JsonObject advancementJson = recipe.serializeAdvancement();
		if (advancementJson != null) {
			final ResourceLocation advancementLocation = vanillaAdvancements ? recipe.getAdvancementId() : new ResourceLocation(recipeLocation.getNamespace(), "recipes/" + recipeLocation.getPath());
			futures.add(CommonDataProvider.saveData(cache, advancementJson, advancementPathProvider.json(advancementLocation)));
		}
	}
	
	protected InventoryChangeTrigger.TriggerInstance has(TagKey<Item> tag) {
		return has(ItemPredicate.Builder.item().of(tag).build());
	}
	
	protected InventoryChangeTrigger.TriggerInstance has(ItemLike item) {
		return has(ItemPredicate.Builder.item().of(item).build());
	}
	
	protected InventoryChangeTrigger.TriggerInstance has(ItemPredicate... predicates) {
		return new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, Ints.ANY, Ints.ANY, Ints.ANY, predicates);
	}
	
	public static Ingredient getIngredientOfTag(TagKey<Item> tag) {
		return Ingredient.of(tag);
	}
	
}
