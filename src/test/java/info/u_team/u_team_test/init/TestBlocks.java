package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.*;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.block.*;
import net.minecraft.item.BlockItem;
import net.minecraftforge.eventbus.api.IEventBus;

public class TestBlocks {
	
	public static final BlockDeferredRegister BLOCKS = BlockDeferredRegister.create(TestMod.MODID);
	
	public static final BlockRegistryObject<BasicBlock, BlockItem> BASIC = BLOCKS.register("basicblock", () -> new BasicBlock("basicblock"));
	
	public static final BlockRegistryObject<BasicTileEntityBlock, BlockItem> BASIC_TILEENTITY = BLOCKS.register("tileentity", () -> new BasicTileEntityBlock("tileentity"));
	
	public static final BlockRegistryObject<BasicEnergyCreatorBlock, BlockItem> BASIC_ENERGY_CREATOR = BLOCKS.register("energy_creator", () -> new BasicEnergyCreatorBlock("energy_creator"));
	
	public static final BlockRegistryObject<BasicFluidInventoryBlock, BlockItem> BASIC_FLUID_INVENTORY = BLOCKS.register("fluid_inventory", () -> new BasicFluidInventoryBlock("fluid_inventory"));
	
	public static void register(IEventBus bus) {
		BLOCKS.register(bus);
	}
	
}
