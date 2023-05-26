package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.BlockRegister;
import info.u_team.u_team_core.api.registry.BlockRegistryEntry;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.block.BasicBlock;
import info.u_team.u_team_test.block.BasicBlockEntityBlock;
import info.u_team.u_team_test.block.BasicEnergyCreatorBlock;
import info.u_team.u_team_test.block.BasicFluidInventoryBlock;
import info.u_team.u_team_test.block.BasicNoItemBlock;
import info.u_team.u_team_test.block.BasicNoItemImplicitBlock;
import net.minecraft.world.item.BlockItem;

public class TestBlocks {
	
	public static final BlockRegister BLOCKS = BlockRegister.create(TestMod.MODID);
	
	public static final BlockRegistryEntry<BasicBlock, BlockItem> BASIC = BLOCKS.register("basic_block", BasicBlock::new);
	public static final BlockRegistryEntry<BasicBlockEntityBlock, BlockItem> BASIC_BLOCKENTITY = BLOCKS.register("basic_block_entity", BasicBlockEntityBlock::new);
	public static final BlockRegistryEntry<BasicEnergyCreatorBlock, BlockItem> BASIC_ENERGY_CREATOR = BLOCKS.register("basic_energy_creator", BasicEnergyCreatorBlock::new);
	public static final BlockRegistryEntry<BasicFluidInventoryBlock, BlockItem> BASIC_FLUID_INVENTORY = BLOCKS.register("basic_fluid_inventory", BasicFluidInventoryBlock::new);
	
	public static final RegistryEntry<BasicNoItemBlock> BASIC_NO_ITEM = BLOCKS.registerBlock("basic_no_item", BasicNoItemBlock::new);
	public static final BlockRegistryEntry<BasicNoItemImplicitBlock, BlockItem> BASIC_NO_ITEM_IMPLICIT = BLOCKS.register("basic_no_item_implicit", BasicNoItemImplicitBlock::new);
	
	public static void register() {
		BLOCKS.register();
	}
	
}
