package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.BlockEntityTypeRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import info.u_team.u_team_test.test_multiloader.blockentity.TestInventoryBlockEntity;
import info.u_team.u_team_test.test_multiloader.blockentity.TestSyncBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TestMultiLoaderBlockEntityTypes {
	
	public static final BlockEntityTypeRegister BLOCK_ENTITY_TYPES = BlockEntityTypeRegister.create(TestMultiLoaderReference.MODID);
	
	public static final RegistryEntry<BlockEntityType<TestSyncBlockEntity>> TEST_SYNC = BLOCK_ENTITY_TYPES.register("test_sync", () -> BlockEntityType.Builder.of(TestSyncBlockEntity::new, TestMultiLoaderBlocks.TEST_SYNC.get()));
	public static final RegistryEntry<BlockEntityType<TestInventoryBlockEntity>> TEST_INVENTORY = BLOCK_ENTITY_TYPES.register("test_inventory", () -> BlockEntityType.Builder.of(TestInventoryBlockEntity.Factory.INSTANCE::create, TestMultiLoaderBlocks.TEST_INVENTORY.get()));
	
	static void register() {
		BLOCK_ENTITY_TYPES.register();
	}
	
}
