package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.BlockRegister;
import info.u_team.u_team_core.api.registry.BlockRegistryEntry;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;

public class TestMultiLoaderBlocks {
	
	public static final BlockRegister BLOCKS = BlockRegister.create(TestMultiLoaderReference.MODID);
	
	public static final BlockRegistryEntry<Block, BlockItem> TEST = BLOCKS.register("test_block", () -> new Block(Properties.of(Material.STONE)), block -> new BlockItem(block, new Item.Properties()));
	
	public static void register() {
		BLOCKS.register();
	}
	
}
