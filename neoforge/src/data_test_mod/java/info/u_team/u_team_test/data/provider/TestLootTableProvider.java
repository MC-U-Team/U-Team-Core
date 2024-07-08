package info.u_team.u_team_test.data.provider;

import info.u_team.u_team_core.data.CommonLootTableProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.init.TestBlocks;
import net.minecraft.world.level.block.Blocks;

public class TestLootTableProvider extends CommonLootTableProvider {
	
	public TestLootTableProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register(LootTableRegister register) {
		registerBlock(TestBlocks.BASIC, addFortuneBlockLootTable(register.registries(), TestBlocks.BASIC.get(), Blocks.ACACIA_LOG), register);
		registerBlock(TestBlocks.BASIC_ENERGY_CREATOR, addBlockEntityBlockLootTable(TestBlocks.BASIC_ENERGY_CREATOR.get()), register);
		registerBlock(TestBlocks.BASIC_FLUID_INVENTORY, addBlockEntityBlockLootTable(TestBlocks.BASIC_FLUID_INVENTORY.get()), register);
	}
	
}
