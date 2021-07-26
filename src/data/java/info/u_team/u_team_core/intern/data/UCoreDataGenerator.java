package info.u_team.u_team_core.intern.data;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_core.intern.data.provider.UCoreBlockStatesProvider;
import info.u_team.u_team_core.intern.data.provider.UCoreLanguagesProvider;
import info.u_team.u_team_core.intern.data.provider.UCoreRecipesProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = UCoreMod.MODID, bus = Bus.MOD)
public class UCoreDataGenerator {

	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final GenerationData data = new GenerationData(UCoreMod.MODID, event);
		if (event.includeServer()) {
			data.addProvider(UCoreRecipesProvider::new);
		}
		if (event.includeClient()) {
			data.addProvider(UCoreLanguagesProvider::new);
			data.addProvider(UCoreBlockStatesProvider::new);
		}
	}
}
