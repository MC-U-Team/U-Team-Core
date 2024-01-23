package info.u_team.u_team_test.test_multiloader.forge.data;

import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderMod;
import info.u_team.u_team_test.test_multiloader.forge.data.provider.ForgeTestMultiLoaderDatapackBuiltinEntriesProvider;
import info.u_team.u_team_test.test_multiloader.forge.data.provider.ForgeTestMultiLoaderGlobalLootModifierProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMultiLoaderMod.MODID, bus = Bus.MOD)
public class ForgeTestMultiLoaderDataGenerator {
	
	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final GenerationData data = new GenerationData(TestMultiLoaderMod.MODID, "forge", event);
		
		data.addProvider(event.includeServer(), ForgeTestMultiLoaderDatapackBuiltinEntriesProvider::new);
		data.addProvider(event.includeServer(), ForgeTestMultiLoaderGlobalLootModifierProvider::new);
	}
}
