package info.u_team.u_team_test.villagerprofession;

import info.u_team.u_team_test.init.*;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.village.MerchantRecipe;
import net.minecraftforge.fml.common.registry.VillagerRegistry.*;

public class VillagerCareerRadiation extends VillagerCareer {
	
	public VillagerCareerRadiation(VillagerProfession parent) {
		super(parent, "radiation");
		addTrade(1, (merchant, recipeList, random) -> {
			recipeList.add(new MerchantRecipe(new ItemStack(Blocks.DIRT), ItemStack.EMPTY, new ItemStack(TestItems.basic)));
			recipeList.add(new MerchantRecipe(new ItemStack(TestItems.basic), ItemStack.EMPTY, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), TestPotionTypes.radiation)));
			recipeList.add(new MerchantRecipe(new ItemStack(TestItems.basic), ItemStack.EMPTY, PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), TestPotionTypes.radiation)));
			recipeList.add(new MerchantRecipe(new ItemStack(TestItems.basic), ItemStack.EMPTY, PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), TestPotionTypes.radiation)));
		});
		addTrade(2, (merchant, recipeList, random) -> {
			recipeList.add(new MerchantRecipe(new ItemStack(TestItems.basic), new ItemStack(TestItems.basic), new ItemStack(TestBlocks.basic, 2)));
			recipeList.add(new MerchantRecipe(new ItemStack(TestBlocks.basic, 20), ItemStack.EMPTY, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), TestPotionTypes.radiation_long)));
			recipeList.add(new MerchantRecipe(new ItemStack(TestBlocks.basic, 20), ItemStack.EMPTY, PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), TestPotionTypes.radiation_long)));
			recipeList.add(new MerchantRecipe(new ItemStack(TestBlocks.basic, 20), ItemStack.EMPTY, PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), TestPotionTypes.radiation_long)));
		});
		addTrade(3, (merchant, recipeList, random) -> {
			recipeList.add(new MerchantRecipe(new ItemStack(TestBlocks.basic, random.nextInt(64)), ItemStack.EMPTY, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), TestPotionTypes.radiation_extreme)));
			recipeList.add(new MerchantRecipe(new ItemStack(TestBlocks.basic, random.nextInt(64)), ItemStack.EMPTY, PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), TestPotionTypes.radiation_extreme)));
			recipeList.add(new MerchantRecipe(new ItemStack(TestBlocks.basic, random.nextInt(64)), ItemStack.EMPTY, PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), TestPotionTypes.radiation_extreme)));
		});
	}
	
}
