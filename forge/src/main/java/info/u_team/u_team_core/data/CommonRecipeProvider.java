package info.u_team.u_team_core.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.google.common.collect.Sets;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
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
		final ResourceLocation recipeLocation = recipe.id();
		
		if (!duplicates.add(recipeLocation)) {
			throw new IllegalStateException("Duplicate recipe " + recipeLocation);
		}
		
		futures.add(CommonDataProvider.saveData(cache, recipe.serializeRecipe(), recipePathProvider.json(recipeLocation)));
		
		final AdvancementHolder advancementHolder = recipe.advancement();
		if (advancementHolder != null) {
			final ResourceLocation advancementLocation = vanillaAdvancements ? advancementHolder.id() : new ResourceLocation(recipeLocation.getNamespace(), "recipes/" + recipeLocation.getPath());
			futures.add(CommonDataProvider.saveData(cache, advancementHolder.value().serializeToJson(), advancementPathProvider.json(advancementLocation)));
		}
	}
	
	protected Criterion<TriggerInstance> has(TagKey<Item> tag) {
		return has(ItemPredicate.Builder.item().of(tag).build());
	}
	
	protected Criterion<TriggerInstance> has(ItemLike item) {
		return has(ItemPredicate.Builder.item().of(item).build());
	}
	
	protected Criterion<TriggerInstance> has(ItemPredicate... predicates) {
		return CriteriaTriggers.INVENTORY_CHANGED.createCriterion(new TriggerInstance(Optional.empty(), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, List.of(predicates)));
	}
	
	public static Ingredient getIngredientOfTag(TagKey<Item> tag) {
		return Ingredient.of(tag);
	}
	
}
