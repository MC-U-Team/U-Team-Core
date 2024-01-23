package info.u_team.u_team_test.test_multiloader.data.forge;

import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderMod;
import info.u_team.u_team_test.test_multiloader.data.provider.forge.TestMultiLoaderForgeDatapackBuiltinEntriesProvider;
import info.u_team.u_team_test.test_multiloader.data.provider.forge.TestMultiLoaderForgeGlobalLootModifierProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMultiLoaderMod.MODID, bus = Bus.MOD)
public class TestMultiLoaderForgeDataGenerator {
	
	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final GenerationData data = new GenerationData(TestMultiLoaderMod.MODID, event);
		
		data.addProvider(event.includeServer(), TestMultiLoaderForgeDatapackBuiltinEntriesProvider::new);
		data.addProvider(event.includeServer(), TestMultiLoaderForgeGlobalLootModifierProvider::new);
	}
}
