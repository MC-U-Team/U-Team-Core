package info.u_team.u_team_test.init;

import info.u_team.u_team_core.tileentitytype.UTileEntityType;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.tileentity.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

public class TestTileEntityTypes {
	
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TestMod.MODID);
	
	public static final RegistryObject<TileEntityType<BasicTileEntityTileEntity>> BASIC = TILE_ENTITY_TYPES.register("tileentity", () -> UTileEntityType.UBuilder.create("tileentity", BasicTileEntityTileEntity::new, TestBlocks.BASIC_TILEENTITY).build());
	
	public static final RegistryObject<TileEntityType<BasicEnergyCreatorTileEntity>> BASIC_ENERGY_CREATOR = TILE_ENTITY_TYPES.register("energy_creator", () -> UTileEntityType.UBuilder.create("energy_creator", BasicEnergyCreatorTileEntity::new, TestBlocks.BASIC_ENERGY_CREATOR).build());
	
	public static final RegistryObject<TileEntityType<BasicFluidInventoryTileEntity>> BASIC_FLUID_INVENTORY = TILE_ENTITY_TYPES.register("fluid_inventory", () -> UTileEntityType.UBuilder.create("fluid_inventory", BasicFluidInventoryTileEntity::new, TestBlocks.BASIC_FLUID_INVENTORY).build());
	
	public static void register(IEventBus bus) {
		TILE_ENTITY_TYPES.register(bus);
	}
	
}
