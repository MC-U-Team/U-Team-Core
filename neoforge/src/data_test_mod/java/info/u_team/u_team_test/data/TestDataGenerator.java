package info.u_team.u_team_test.data;

import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.data.provider.TestBlockStateProvider;
import info.u_team.u_team_test.data.provider.TestBlockTagsProvider;
import info.u_team.u_team_test.data.provider.TestFluidTagsProvider;
import info.u_team.u_team_test.data.provider.TestItemModelProvider;
import info.u_team.u_team_test.data.provider.TestItemTagsProvider;
import info.u_team.u_team_test.data.provider.TestLanguagesProvider;
import info.u_team.u_team_test.data.provider.TestLootTableProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestDataGenerator {
	
	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final GenerationData data = new GenerationData(TestMod.MODID, event);
		
		data.addProvider(event.includeServer(), TestBlockTagsProvider::new, TestItemTagsProvider::new);
		data.addProvider(event.includeServer(), TestFluidTagsProvider::new);
		data.addProvider(event.includeServer(), TestLootTableProvider::new);
		
		data.addProvider(event.includeClient(), TestBlockStateProvider::new);
		data.addProvider(event.includeClient(), TestItemModelProvider::new);
		data.addProvider(event.includeClient(), TestLanguagesProvider::new);
	}
}
