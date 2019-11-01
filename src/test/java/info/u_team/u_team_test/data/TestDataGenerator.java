package info.u_team.u_team_test.data;

import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.data.provider.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestDataGenerator {
	
	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final DataGenerator generator = event.getGenerator();
		if (event.includeServer()) {
		}
		if (event.includeClient()) {
			generator.addProvider(new TestBlockStatesProvider(generator, TestMod.MODID, event.getExistingFileHelper()));
			generator.addProvider(new TestItemModelsProvider(generator, TestMod.MODID, event.getExistingFileHelper()));
		}
	}
	
}
