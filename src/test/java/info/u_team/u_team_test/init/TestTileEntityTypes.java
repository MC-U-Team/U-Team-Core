package info.u_team.u_team_test.init;

import info.u_team.u_team_core.tileentitytype.UTileEntityType;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.tileentity.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

public class TestTileEntityTypes {
	
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TestMod.MODID);
	
	public static final TileEntityType<BasicTileEntityTileEntity> BASIC = UTileEntityType.UBuilder.create("tileentity", BasicTileEntityTileEntity::new, TestBlocks.BASIC_TILEENTITY).build();
	
	public static final TileEntityType<BasicEnergyCreatorTileEntity> BASIC_ENERGY_CREATOR = UTileEntityType.UBuilder.create("energy_creator", BasicEnergyCreatorTileEntity::new, TestBlocks.BASIC_ENERGY_CREATOR).build();
	
	public static final TileEntityType<BasicFluidInventoryTileEntity> BASIC_FLUID_INVENTORY = UTileEntityType.UBuilder.create("fluid_inventory", BasicFluidInventoryTileEntity::new, TestBlocks.BASIC_FLUID_INVENTORY).build();
	
	public static void register(IEventBus bus) {
		TILE_ENTITY_TYPES.register(bus);
	}
	
}
