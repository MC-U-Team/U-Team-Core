package info.u_team.u_team_test.test_multiloader.data.provider;

import info.u_team.u_team_core.data.CommonLootTableProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderBlocks;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderItems;

public class TestMultiLoaderLootTableProvider extends CommonLootTableProvider {
	
	public TestMultiLoaderLootTableProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register(LootTableRegister register) {
		registerBlock(TestMultiLoaderBlocks.TEST, addFortuneBlockLootTable(register.registries(), TestMultiLoaderBlocks.TEST.get(), TestMultiLoaderItems.TEST.get()), register);
		registerBlock(TestMultiLoaderBlocks.TEST_SYNC, addBlockEntityBlockLootTable(TestMultiLoaderBlocks.TEST_SYNC.get()), register);
		registerBlock(TestMultiLoaderBlocks.TEST_INVENTORY, addBlockEntityBlockLootTable(TestMultiLoaderBlocks.TEST_INVENTORY.get()), register);
	}
}
