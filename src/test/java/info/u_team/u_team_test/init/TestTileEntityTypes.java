package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.TileEntityTypeDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.tileentity.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;

public class TestTileEntityTypes {
	
	public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = TileEntityTypeDeferredRegister.create(TestMod.MODID);
	
	public static final RegistryObject<TileEntityType<BasicTileEntityTileEntity>> BASIC = TILE_ENTITY_TYPES.register("tileentity", () -> TileEntityType.Builder.create(BasicTileEntityTileEntity::new, TestBlocks.BASIC_TILEENTITY.get()));
	
	public static final RegistryObject<TileEntityType<BasicEnergyCreatorTileEntity>> BASIC_ENERGY_CREATOR = TILE_ENTITY_TYPES.register("energy_creator", () -> TileEntityType.Builder.create(BasicEnergyCreatorTileEntity::new, TestBlocks.BASIC_ENERGY_CREATOR.get()));
	
	public static final RegistryObject<TileEntityType<BasicFluidInventoryTileEntity>> BASIC_FLUID_INVENTORY = TILE_ENTITY_TYPES.register("fluid_inventory", () -> TileEntityType.Builder.create(BasicFluidInventoryTileEntity::new, TestBlocks.BASIC_FLUID_INVENTORY.get()));
	
	public static void registerMod(IEventBus bus) {
		TILE_ENTITY_TYPES.register(bus);
	}
	
}
