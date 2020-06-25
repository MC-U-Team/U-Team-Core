package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.BlockDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.block.*;
import net.minecraftforge.eventbus.api.IEventBus;

public class TestBlocks {
	
	public static final BlockDeferredRegister BLOCKS = BlockDeferredRegister.create(TestMod.MODID);
	
	public static final BasicBlock BASIC = new BasicBlock("basicblock");
	
	public static final BasicTileEntityBlock BASIC_TILEENTITY = new BasicTileEntityBlock("tileentity");
	
	public static final BasicEnergyCreatorBlock BASIC_ENERGY_CREATOR = new BasicEnergyCreatorBlock("energy_creator");
	
	public static final BasicFluidInventoryBlock BASIC_FLUID_INVENTORY = new BasicFluidInventoryBlock("fluid_inventory");
	
	public static void register(IEventBus bus) {
		BLOCKS.register(bus);
	}
	
}
