package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.TileEntityTypeDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.tileentity.BasicEnergyCreatorTileEntity;
import info.u_team.u_team_test.tileentity.BasicFluidInventoryTileEntity;
import info.u_team.u_team_test.tileentity.BasicTileEntityTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;

public class TestTileEntityTypes {
	
	public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = TileEntityTypeDeferredRegister.create(TestMod.MODID);
	
	public static final RegistryObject<BlockEntityType<BasicTileEntityTileEntity>> BASIC = TILE_ENTITY_TYPES.register("tileentity", () -> BlockEntityType.Builder.of(BasicTileEntityTileEntity::new, TestBlocks.BASIC_BLOCKENTITY.get()));
	
	public static final RegistryObject<BlockEntityType<BasicEnergyCreatorTileEntity>> BASIC_ENERGY_CREATOR = TILE_ENTITY_TYPES.register("energy_creator", () -> BlockEntityType.Builder.of(BasicEnergyCreatorTileEntity::new, TestBlocks.BASIC_ENERGY_CREATOR.get()));
	
	public static final RegistryObject<BlockEntityType<BasicFluidInventoryTileEntity>> BASIC_FLUID_INVENTORY = TILE_ENTITY_TYPES.register("fluid_inventory", () -> BlockEntityType.Builder.of(BasicFluidInventoryTileEntity::new, TestBlocks.BASIC_FLUID_INVENTORY.get()));
	
	public static void registerMod(IEventBus bus) {
		TILE_ENTITY_TYPES.register(bus);
	}
	
}
