package info.u_team.u_team_test.data.provider;

import static info.u_team.u_team_test.init.TestTags.Blocks.TEST_TAG_1;
import static info.u_team.u_team_test.init.TestTags.Blocks.TEST_TAG_2;

import info.u_team.u_team_core.data.CommonBlockTagsProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_core.item.tier.VanillaTierTags;
import info.u_team.u_team_test.init.TestBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

public class TestBlockTagsProvider extends CommonBlockTagsProvider {
	
	public TestBlockTagsProvider(GenerationData generationData) {
		super(generationData);
	}
	
	@Override
	public void register(HolderLookup.Provider provider) {
		// Add block tags for mining
		tag(VanillaTierTags.MINEABLE_WITH_SHOVEL).add(TestBlocks.BASIC.get());
		tag(VanillaTierTags.NEEDS_STONE_TOOL).add(TestBlocks.BASIC.get());
		
		tag(VanillaTierTags.MINEABLE_WITH_PICKAXE).add(TestBlocks.BASIC_ENERGY_CREATOR.get());
		tag(VanillaTierTags.NEEDS_IRON_TOOL).add(TestBlocks.BASIC_ENERGY_CREATOR.get());
		
		tag(VanillaTierTags.MINEABLE_WITH_PICKAXE).add(TestBlocks.BASIC_FLUID_INVENTORY.get());
		tag(VanillaTierTags.NEEDS_IRON_TOOL).add(TestBlocks.BASIC_FLUID_INVENTORY.get());
		
		// Add normal tags
		tag(TEST_TAG_1).add(Blocks.ACACIA_BUTTON, Blocks.ACACIA_DOOR);
		
		tag(TEST_TAG_2).addTag(TEST_TAG_1).add(Blocks.ACACIA_LOG);
		
		tag(TEST_TAG_1).add(Blocks.BIRCH_LEAVES);
		tag(TEST_TAG_1).addTag(BlockTags.BEDS);
		
		tag(TEST_TAG_2).add(Blocks.ACACIA_LOG);
		tag(TEST_TAG_2).addTag(TEST_TAG_1);
	}
	
}
