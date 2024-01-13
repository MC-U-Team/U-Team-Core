package info.u_team.u_team_core.intern.data;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_core.intern.data.provider.UCoreBlockStatesProvider;
import info.u_team.u_team_core.intern.data.provider.UCoreLanguagesProvider;
import info.u_team.u_team_core.intern.data.provider.UCoreRecipesProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = UCoreMod.MODID, bus = Bus.MOD)
public class UCoreDataGenerator {
	
	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final GenerationData data = new GenerationData(UCoreMod.MODID, event);
		
		data.addProvider(event.includeServer(), UCoreRecipesProvider::new);
		
		data.addProvider(event.includeClient(), UCoreLanguagesProvider::new);
		data.addProvider(event.includeClient(), UCoreBlockStatesProvider::new);
	}
}
