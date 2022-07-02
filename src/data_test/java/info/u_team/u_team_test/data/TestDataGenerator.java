package info.u_team.u_team_test.data;

import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.data.provider.TestBlockStateProvider;
import info.u_team.u_team_test.data.provider.TestBlockTagsProvider;
import info.u_team.u_team_test.data.provider.TestGlobalLootModifierProvider;
import info.u_team.u_team_test.data.provider.TestItemModelProvider;
import info.u_team.u_team_test.data.provider.TestItemTagsProvider;
import info.u_team.u_team_test.data.provider.TestJsonCodecsProvider;
import info.u_team.u_team_test.data.provider.TestLanguagesProvider;
import info.u_team.u_team_test.data.provider.TestLootTableProvider;
import info.u_team.u_team_test.data.provider.TestRecipeProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestDataGenerator {
	
	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final GenerationData data = new GenerationData(TestMod.MODID, event);
		
		data.addProvider(event.includeServer(), TestBlockTagsProvider::new, TestItemTagsProvider::new);
		data.addProvider(event.includeServer(), TestLootTableProvider::new);
		data.addProvider(event.includeServer(), TestGlobalLootModifierProvider::new);
		data.addProvider(event.includeServer(), TestJsonCodecsProvider::new);
		data.addProvider(event.includeServer(), TestRecipeProvider::new);
		
		data.addProvider(event.includeClient(), TestBlockStateProvider::new);
		data.addProvider(event.includeClient(), TestItemModelProvider::new);
		data.addProvider(event.includeClient(), TestLanguagesProvider::new);
	}
}
