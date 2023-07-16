package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.BlockEntityTypeRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.blockentity.BasicEnergyCreatorBlockEntity;
import info.u_team.u_team_test.blockentity.BasicFluidInventoryBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TestBlockEntityTypes {
	
	public static final BlockEntityTypeRegister BLOCK_ENTITY_TYPES = BlockEntityTypeRegister.create(TestMod.MODID);
	
	public static final RegistryEntry<BlockEntityType<BasicEnergyCreatorBlockEntity>> BASIC_ENERGY_CREATOR = BLOCK_ENTITY_TYPES.register("energy_creator", () -> BlockEntityType.Builder.of(BasicEnergyCreatorBlockEntity::new, TestBlocks.BASIC_ENERGY_CREATOR.get()));
	public static final RegistryEntry<BlockEntityType<BasicFluidInventoryBlockEntity>> BASIC_FLUID_INVENTORY = BLOCK_ENTITY_TYPES.register("fluid_inventory", () -> BlockEntityType.Builder.of(BasicFluidInventoryBlockEntity::new, TestBlocks.BASIC_FLUID_INVENTORY.get()));
	
	static void register() {
		BLOCK_ENTITY_TYPES.register();
	}
	
}
