package info.u_team.u_team_test.data;

import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.data.provider.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestDataGenerator {
	
	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final GenerationData data = new GenerationData(TestMod.MODID, event);
		if (event.includeServer()) {
			data.addProvider(TestBlockTagsProvider::new);
			data.addProvider(TestItemTagsProvider::new);
			data.addProvider(TestLootTablesProvider::new);
			data.addProvider(TestGlobalLootModifiersProvider::new);
		}
		if (event.includeClient()) {
			data.addProvider(TestBlockStatesProvider::new);
			data.addProvider(TestItemModelsProvider::new);
			data.addProvider(TestLanguagesProvider::new);
		}
	}
}
