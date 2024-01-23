package info.u_team.u_team_test.test_multiloader.neoforge.data;

import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderMod;
import info.u_team.u_team_test.test_multiloader.neoforge.data.provider.NeoForgeTestMultiLoaderDatapackBuiltinEntriesProvider;
import info.u_team.u_team_test.test_multiloader.neoforge.data.provider.NeoForgeTestMultiLoaderGlobalLootModifierProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = TestMultiLoaderMod.MODID, bus = Bus.MOD)
public class NeoForgeTestMultiLoaderDataGenerator {
	
	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final GenerationData data = new GenerationData(TestMultiLoaderMod.MODID, "forge", event);
		
		data.addProvider(event.includeServer(), NeoForgeTestMultiLoaderDatapackBuiltinEntriesProvider::new);
		data.addProvider(event.includeServer(), NeoForgeTestMultiLoaderGlobalLootModifierProvider::new);
	}
}
