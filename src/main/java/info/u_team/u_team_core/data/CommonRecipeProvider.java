package info.u_team.u_team_core.data;

import java.io.IOException;
import java.util.Set;
import java.util.function.Consumer;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;

import info.u_team.u_team_core.util.TagUtil;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator.PathProvider;
import net.minecraft.data.DataGenerator.Target;
import net.minecraft.data.DataProvider;
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
		
		recipePathProvider = generationData.generator().createPathProvider(Target.DATA_PACK, "recipes");
		advancementPathProvider = generationData.generator().createPathProvider(Target.DATA_PACK, "advancements");
	}
	
	@Override
	public GenerationData getGenerationData() {
		return generationData;
	}
	
	@Override
	public void run(CachedOutput cache) throws IOException {
		final Set<ResourceLocation> duplicates = Sets.newHashSet();
		register(recipe -> generateRecipe(cache, recipe, duplicates, false));
		registerVanilla(recipe -> generateRecipe(cache, recipe, duplicates, true));
	}
	
	@Override
	public String getName() {
		return "Recipe";
	}
	
	public void registerVanilla(Consumer<FinishedRecipe> consumer) {
	}
	
	private void generateRecipe(CachedOutput cache, FinishedRecipe recipe, Set<ResourceLocation> duplicates, boolean vanillaAdvancements) {
		final ResourceLocation recipeLocation = recipe.getId();
		
		if (!duplicates.add(recipeLocation)) {
			throw new IllegalStateException("Duplicate recipe " + recipeLocation);
		}
		
		CommonDataProvider.saveData(cache, recipe.serializeRecipe(), recipePathProvider.json(recipeLocation), "Could not save recipe");
		
		final JsonObject advancementJson = recipe.serializeAdvancement();
		if (advancementJson != null) {
			final ResourceLocation advancementLocation = vanillaAdvancements ? recipe.getAdvancementId() : new ResourceLocation(recipeLocation.getNamespace(), "recipes/" + recipeLocation.getPath());
			CommonDataProvider.saveData(cache, advancementJson, advancementPathProvider.json(advancementLocation), "Could not save advancement");
		}
	}
	
	protected InventoryChangeTrigger.TriggerInstance has(TagKey<Item> tag) {
		return has(ItemPredicate.Builder.item().of(tag).build());
	}
	
	protected InventoryChangeTrigger.TriggerInstance has(ItemLike item) {
		return has(ItemPredicate.Builder.item().of(item).build());
	}
	
	protected InventoryChangeTrigger.TriggerInstance has(ItemPredicate... predicates) {
		return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, Ints.ANY, Ints.ANY, Ints.ANY, predicates);
	}
	
	public static Ingredient getIngredientOfTag(TagKey<Item> tag) {
		return TagUtil.getSerializableIngredientOfTag(tag);
	}
	
}
