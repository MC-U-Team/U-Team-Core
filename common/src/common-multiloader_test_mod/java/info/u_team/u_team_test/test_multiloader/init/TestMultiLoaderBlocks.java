package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.BlockRegister;
import info.u_team.u_team_core.api.registry.BlockRegistryEntry;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import info.u_team.u_team_test.test_multiloader.block.TestInventoryBlock;
import info.u_team.u_team_test.test_multiloader.block.TestNoItemBlock;
import info.u_team.u_team_test.test_multiloader.block.TestNoItemImplicitBlock;
import info.u_team.u_team_test.test_multiloader.block.TestSyncBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;

public class TestMultiLoaderBlocks {
	
	public static final BlockRegister BLOCKS = BlockRegister.create(TestMultiLoaderReference.MODID);
	
	public static final BlockRegistryEntry<UBlock, BlockItem> TEST = BLOCKS.register("test_block", () -> new UBlock(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3).sound(SoundType.GRAVEL).friction(0.8F).lightLevel(state -> 1), new Item.Properties().rarity(Rarity.UNCOMMON)));
	public static final BlockRegistryEntry<TestSyncBlock, BlockItem> TEST_SYNC = BLOCKS.register("test_sync_block", TestSyncBlock::new);
	public static final BlockRegistryEntry<TestInventoryBlock, BlockItem> TEST_INVENTORY = BLOCKS.register("test_inventory_block", TestInventoryBlock::new);
	
	public static final RegistryEntry<TestNoItemBlock> TEST_NO_ITEM = BLOCKS.registerBlock("test_no_item", TestNoItemBlock::new);
	public static final BlockRegistryEntry<TestNoItemImplicitBlock, BlockItem> TEST_NO_ITEM_IMPLICIT = BLOCKS.register("test_no_item_implicit", TestNoItemImplicitBlock::new);
	
	static void register() {
		BLOCKS.register();
	}
	
}
