package info.u_team.u_team_test.test_multiloader.data.provider;

import info.u_team.u_team_core.data.CommonRecipeProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderItems;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

public class TestMultiLoaderRecipeProvider extends CommonRecipeProvider {
	
	public TestMultiLoaderRecipeProvider(GenerationData generationData) {
		super(generationData);
	}
	
	@Override
	public void register(RecipeOutput output) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TestMultiLoaderItems.TEST.get()) //
				.pattern("WLW") //
				.pattern("ESE") //
				.pattern("WLW") //
				.define('W', ItemTags.WOOL) //
				.define('L', Items.ACACIA_FENCE) //
				.define('E', Items.ENDER_CHEST) //
				.define('S', Items.ALLIUM) //
				.unlockedBy("has_anvil", has(Items.ANVIL)) //
				.save(output);
	}
	
	@Override
	public void registerVanilla(RecipeOutput output) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TestMultiLoaderItems.TEST.get()) //
				.pattern("WLW") //
				.pattern("ESE") //
				.pattern("WLW") //
				.define('W', ItemTags.WOOL) //
				.define('L', Items.ACACIA_BUTTON) //
				.define('E', Items.ENDER_CHEST) //
				.define('S', Items.ALLIUM) //
				.unlockedBy("has_anvil", has(Items.ANVIL)) //
				.save(output, modid() + ":test_item_vanilla");
	}
	
}
