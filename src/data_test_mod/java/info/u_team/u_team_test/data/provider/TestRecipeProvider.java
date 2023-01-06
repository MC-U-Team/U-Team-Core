package info.u_team.u_team_test.data.provider;

import java.util.function.Consumer;

import info.u_team.u_team_core.data.CommonRecipeProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.init.TestItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

public class TestRecipeProvider extends CommonRecipeProvider {
	
	public TestRecipeProvider(GenerationData generationData) {
		super(generationData);
	}
	
	@Override
	public void register(Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TestItems.BASIC.get()) //
				.pattern("WLW") //
				.pattern("ESE") //
				.pattern("WLW") //
				.define('W', getIngredientOfTag(ItemTags.WOOL)) //
				.define('L', Items.ACACIA_FENCE) //
				.define('E', Items.ENDER_CHEST) //
				.define('S', Items.ALLIUM) //
				.unlockedBy("has_anvil", has(Items.ANVIL)) //
				.save(consumer);
	}
	
	@Override
	public void registerVanilla(Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TestItems.BASIC.get()) //
				.pattern("WLW") //
				.pattern("ESE") //
				.pattern("WLW") //
				.define('W', getIngredientOfTag(ItemTags.WOOL)) //
				.define('L', Items.ACACIA_BUTTON) //
				.define('E', Items.ENDER_CHEST) //
				.define('S', Items.ALLIUM) //
				.unlockedBy("has_anvil", has(Items.ANVIL)) //
				.save(consumer, modid() + ":basic_item_vanilla");
	}
	
}
