package info.u_team.u_team_test.test_multiloader.data.provider;

import java.util.function.BiConsumer;

import info.u_team.u_team_core.data.CommonLootTableProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderBlocks;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

public class TestMultiLoaderLootTableProvider extends CommonLootTableProvider {
	
	public TestMultiLoaderLootTableProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register(BiConsumer<ResourceLocation, LootTable> consumer) {
		registerBlock(TestMultiLoaderBlocks.TEST, addFortuneBlockLootTable(TestMultiLoaderBlocks.TEST.get(), TestMultiLoaderItems.TEST.get()), consumer);
		registerBlock(TestMultiLoaderBlocks.TEST_SYNC, addBlockEntityBlockLootTable(TestMultiLoaderBlocks.TEST_SYNC.get()), consumer);
		registerBlock(TestMultiLoaderBlocks.TEST_INVENTORY, addBlockEntityBlockLootTable(TestMultiLoaderBlocks.TEST_INVENTORY.get()), consumer);
	}
	
}
