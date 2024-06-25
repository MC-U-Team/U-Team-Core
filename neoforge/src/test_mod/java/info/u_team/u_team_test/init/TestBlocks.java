package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.BlockRegister;
import info.u_team.u_team_core.api.registry.BlockRegistryEntry;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.block.BasicBlock;
import info.u_team.u_team_test.block.BasicEnergyCreatorBlock;
import info.u_team.u_team_test.block.BasicFluidInventoryBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class TestBlocks {
	
	public static final BlockRegister BLOCKS = BlockRegister.create(TestMod.MODID);
	
	public static final BlockRegistryEntry<BasicBlock, BlockItem> BASIC = BLOCKS.register("basic_block", BasicBlock::new);
	public static final BlockRegistryEntry<BasicEnergyCreatorBlock, BlockItem> BASIC_ENERGY_CREATOR = BLOCKS.register("basic_energy_creator", BasicEnergyCreatorBlock::new);
	public static final BlockRegistryEntry<BasicFluidInventoryBlock, BlockItem> BASIC_FLUID_INVENTORY = BLOCKS.register("basic_fluid_inventory", BasicFluidInventoryBlock::new);
	
	public static final RegistryEntry<LiquidBlock> TEST_FLUID = BLOCKS.registerBlock("test_fluid", () -> new LiquidBlock(TestFluids.TEST_FLUID.get(), Properties.of().mapColor(MapColor.COLOR_RED).replaceable().noCollission().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)));
	
	static void register() {
		BLOCKS.register();
	}
	
}
