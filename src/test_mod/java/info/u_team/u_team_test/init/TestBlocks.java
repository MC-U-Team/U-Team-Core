package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.BlockDeferredRegister;
import info.u_team.u_team_core.util.registry.BlockRegistryObject;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.block.BasicBlock;
import info.u_team.u_team_test.block.BasicBlockEntityBlock;
import info.u_team.u_team_test.block.BasicEnergyCreatorBlock;
import info.u_team.u_team_test.block.BasicFluidInventoryBlock;
import info.u_team.u_team_test.block.BasicSyncBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.eventbus.api.IEventBus;

public class TestBlocks {
	
	public static final BlockDeferredRegister BLOCKS = BlockDeferredRegister.create(TestMod.MODID);
	
	public static final BlockRegistryObject<BasicBlock, BlockItem> BASIC = BLOCKS.register("basic_block", BasicBlock::new);
	public static final BlockRegistryObject<BasicBlockEntityBlock, BlockItem> BASIC_BLOCKENTITY = BLOCKS.register("basic_block_entity", BasicBlockEntityBlock::new);
	public static final BlockRegistryObject<BasicEnergyCreatorBlock, BlockItem> BASIC_ENERGY_CREATOR = BLOCKS.register("basic_energy_creator", BasicEnergyCreatorBlock::new);
	public static final BlockRegistryObject<BasicFluidInventoryBlock, BlockItem> BASIC_FLUID_INVENTORY = BLOCKS.register("basic_fluid_inventory", BasicFluidInventoryBlock::new);
	public static final BlockRegistryObject<BasicSyncBlock, BlockItem> BASIC_SYNC = BLOCKS.register("basic_sync", BasicSyncBlock::new);
	
	public static void registerMod(IEventBus bus) {
		BLOCKS.register(bus);
	}
	
}
