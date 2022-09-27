package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.BlockEntityTypeDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.blockentity.BasicBlockEntityBlockEntity;
import info.u_team.u_team_test.blockentity.BasicEnergyCreatorBlockEntity;
import info.u_team.u_team_test.blockentity.BasicFluidInventoryBlockEntity;
import info.u_team.u_team_test.blockentity.BasicSyncBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class TestBlockEntityTypes {
	
	public static final BlockEntityTypeDeferredRegister BLOCK_ENTITY_TYPES = BlockEntityTypeDeferredRegister.create(TestMod.MODID);
	
	public static final RegistryObject<BlockEntityType<BasicBlockEntityBlockEntity>> BASIC = BLOCK_ENTITY_TYPES.register("basic_block_entity", () -> BlockEntityType.Builder.of(BasicBlockEntityBlockEntity::new, TestBlocks.BASIC_BLOCKENTITY.get()));
	public static final RegistryObject<BlockEntityType<BasicEnergyCreatorBlockEntity>> BASIC_ENERGY_CREATOR = BLOCK_ENTITY_TYPES.register("energy_creator", () -> BlockEntityType.Builder.of(BasicEnergyCreatorBlockEntity::new, TestBlocks.BASIC_ENERGY_CREATOR.get()));
	public static final RegistryObject<BlockEntityType<BasicFluidInventoryBlockEntity>> BASIC_FLUID_INVENTORY = BLOCK_ENTITY_TYPES.register("fluid_inventory", () -> BlockEntityType.Builder.of(BasicFluidInventoryBlockEntity::new, TestBlocks.BASIC_FLUID_INVENTORY.get()));
	public static final RegistryObject<BlockEntityType<BasicSyncBlockEntity>> BASIC_SYNC = BLOCK_ENTITY_TYPES.register("basic_sync", () -> BlockEntityType.Builder.of(BasicSyncBlockEntity::new, TestBlocks.BASIC_SYNC.get()));
	
	public static void registerMod(IEventBus bus) {
		BLOCK_ENTITY_TYPES.register(bus);
	}
	
}
