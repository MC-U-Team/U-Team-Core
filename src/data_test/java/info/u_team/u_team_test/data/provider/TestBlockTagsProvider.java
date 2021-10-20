package info.u_team.u_team_test.data.provider;

import static info.u_team.u_team_test.init.TestTags.Blocks.TEST_TAG_1;
import static info.u_team.u_team_test.init.TestTags.Blocks.TEST_TAG_2;

import info.u_team.u_team_core.data.CommonBlockTagsProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_core.item.tier.VanillaTierTags;
import info.u_team.u_team_test.init.TestBlocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

public class TestBlockTagsProvider extends CommonBlockTagsProvider {
	
	public TestBlockTagsProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerTags() {
		// Add block tags for mining
		getBuilder(VanillaTierTags.MINEABLE_WITH_SHOVEL).add(TestBlocks.BASIC.get());
		getBuilder(VanillaTierTags.NEEDS_STONE_TOOL).add(TestBlocks.BASIC.get());
		
		getBuilder(VanillaTierTags.MINEABLE_WITH_PICKAXE).add(TestBlocks.BASIC_BLOCKENTITY.get());
		getBuilder(VanillaTierTags.NEEDS_IRON_TOOL).add(TestBlocks.BASIC_BLOCKENTITY.get());
		
		getBuilder(VanillaTierTags.MINEABLE_WITH_PICKAXE).add(TestBlocks.BASIC_ENERGY_CREATOR.get());
		getBuilder(VanillaTierTags.NEEDS_IRON_TOOL).add(TestBlocks.BASIC_ENERGY_CREATOR.get());
		
		getBuilder(VanillaTierTags.MINEABLE_WITH_PICKAXE).add(TestBlocks.BASIC_FLUID_INVENTORY.get());
		getBuilder(VanillaTierTags.NEEDS_IRON_TOOL).add(TestBlocks.BASIC_FLUID_INVENTORY.get());
		
		// Add normal tags
		getBuilder(TEST_TAG_1).add(Blocks.ACACIA_BUTTON, Blocks.ACACIA_DOOR);
		
		getBuilder(TEST_TAG_2).add(TEST_TAG_1).add(Blocks.ACACIA_LOG);
		
		getBuilder(TEST_TAG_1).add(Blocks.BIRCH_LEAVES);
		getBuilder(TEST_TAG_1).add(BlockTags.BEDS);
		
		getBuilder(TEST_TAG_2).add(Blocks.ACACIA_LOG);
		getBuilder(TEST_TAG_2).add(TEST_TAG_1);
	}
	
}
