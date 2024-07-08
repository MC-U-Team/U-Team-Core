package info.u_team.u_team_core.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import com.google.common.collect.Sets;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput.PathProvider;
import net.minecraft.data.PackOutput.Target;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.WithConditions;

public abstract class CommonRecipeProvider implements DataProvider, CommonDataProvider<RecipeOutput> {
	
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
		final Function<Boolean, RecipeOutput> recipeOutputCreator = vanillaAdvancements -> new RecipeOutput() {
			
			@SuppressWarnings("removal")
			@Override
			public Builder advancement() {
				return Advancement.Builder.recipeAdvancement().parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT);
			}
			
			@Override
			public void accept(ResourceLocation id, Recipe<?> recipe, AdvancementHolder advancement, ICondition... conditions) {
				generateRecipe(cache, id, recipe, advancement, conditions, duplicates, vanillaAdvancements, futures);
			}
		};
		register(recipeOutputCreator.apply(false));
		registerVanilla(recipeOutputCreator.apply(true));
		return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
	}
	
	@Override
	public String getName() {
		return "Recipe: " + nameSuffix();
	}
	
	public void registerVanilla(RecipeOutput consumer) {
	}
	
	private void generateRecipe(CachedOutput cache, ResourceLocation id, Recipe<?> recipe, AdvancementHolder advancement, ICondition[] conditions, Set<ResourceLocation> duplicates, boolean vanillaAdvancements, List<CompletableFuture<?>> futures) {
		if (!duplicates.add(id)) {
			throw new IllegalStateException("Duplicate recipe " + id);
		}
		
		futures.add(saveData(cache, Recipe.CONDITIONAL_CODEC, Optional.of(new WithConditions<>(recipe, conditions)), recipePathProvider.json(id)));
		
		if (advancement != null) {
			final ResourceLocation advancementLocation = vanillaAdvancements ? advancement.id() : ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "recipes/" + id.getPath());
			futures.add(saveData(cache, Advancement.CONDITIONAL_CODEC, Optional.of(new WithConditions<>(advancement.value(), conditions)), advancementPathProvider.json(advancementLocation)));
		}
	}
	
	protected Criterion<TriggerInstance> has(TagKey<Item> tag) {
		return has(ItemPredicate.Builder.item().of(tag).build());
	}
	
	protected Criterion<TriggerInstance> has(ItemLike item) {
		return has(ItemPredicate.Builder.item().of(item).build());
	}
	
	protected Criterion<TriggerInstance> has(ItemPredicate... predicates) {
		return CriteriaTriggers.INVENTORY_CHANGED.createCriterion(new InventoryChangeTrigger.TriggerInstance(Optional.empty(), InventoryChangeTrigger.TriggerInstance.Slots.ANY, List.of(predicates)));
	}
	
}
