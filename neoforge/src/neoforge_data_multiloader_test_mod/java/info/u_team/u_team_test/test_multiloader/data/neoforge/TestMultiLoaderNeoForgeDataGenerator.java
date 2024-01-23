package info.u_team.u_team_test.test_multiloader.data.neoforge;

import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderMod;
import info.u_team.u_team_test.test_multiloader.data.provider.neoforge.TestMultiLoaderNeoForgeDatapackBuiltinEntriesProvider;
import info.u_team.u_team_test.test_multiloader.data.provider.neoforge.TestMultiLoaderNeoForgeGlobalLootModifierProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = TestMultiLoaderMod.MODID, bus = Bus.MOD)
public class TestMultiLoaderNeoForgeDataGenerator {
	
	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final GenerationData data = new GenerationData(TestMultiLoaderMod.MODID, event);
		
		data.addProvider(event.includeServer(), TestMultiLoaderNeoForgeDatapackBuiltinEntriesProvider::new);
		data.addProvider(event.includeServer(), TestMultiLoaderNeoForgeGlobalLootModifierProvider::new);
	}
}
