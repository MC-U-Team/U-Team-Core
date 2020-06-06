package info.u_team.u_team_core.intern.data;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_core.intern.data.provider.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = UCoreMain.MODID, bus = Bus.MOD)
public class UCoreDataGenerator {
	
	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final GenerationData data = new GenerationData(UCoreMain.MODID, event);
		if (event.includeServer()) {
			data.addProvider(UCoreRecipesProvider::new);
		}
		if (event.includeClient()) {
			data.addProvider(UCoreLanguagesProvider::new);
			data.addProvider(UCoreBlockStatesProvider::new);
		}
	}
}
