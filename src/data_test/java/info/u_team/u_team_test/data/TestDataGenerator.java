package info.u_team.u_team_test.data;

import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.data.provider.TestBlockStatesProvider;
import info.u_team.u_team_test.data.provider.TestBlockTagsProvider;
import info.u_team.u_team_test.data.provider.TestGlobalLootModifiersProvider;
import info.u_team.u_team_test.data.provider.TestItemModelsProvider;
import info.u_team.u_team_test.data.provider.TestItemTagsProvider;
import info.u_team.u_team_test.data.provider.TestLanguagesProvider;
import info.u_team.u_team_test.data.provider.TestLootTablesProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestDataGenerator {
	
	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final GenerationData data = new GenerationData(TestMod.MODID, event);
		final TestBlockTagsProvider blockTags = data.addProvider(event.includeServer(), TestBlockTagsProvider::new);
		data.addProvider(event.includeServer(), dataX -> new TestItemTagsProvider(dataX, blockTags));
		data.addProvider(event.includeServer(), TestLootTablesProvider::new);
		data.addProvider(event.includeServer(), TestGlobalLootModifiersProvider::new);
		
		data.addProvider(event.includeClient(), TestBlockStatesProvider::new);
		data.addProvider(event.includeClient(), TestItemModelsProvider::new);
		data.addProvider(event.includeClient(), TestLanguagesProvider::new);
	}
}
