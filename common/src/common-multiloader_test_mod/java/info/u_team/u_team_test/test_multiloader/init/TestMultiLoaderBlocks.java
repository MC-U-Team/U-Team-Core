package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.BlockRegister;
import info.u_team.u_team_core.api.registry.BlockRegistryEntry;
import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import info.u_team.u_team_test.test_multiloader.block.TestSyncBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;

public class TestMultiLoaderBlocks {
	
	public static final BlockRegister BLOCKS = BlockRegister.create(TestMultiLoaderReference.MODID);
	
	public static final BlockRegistryEntry<UBlock, BlockItem> TEST = BLOCKS.register("test_block", () -> new UBlock(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3)));
	public static final BlockRegistryEntry<UBlock, BlockItem> TEST_SYNC = BLOCKS.register("test_sync_block", TestSyncBlock::new);
	
	static void register() {
		BLOCKS.register();
	}
	
}
