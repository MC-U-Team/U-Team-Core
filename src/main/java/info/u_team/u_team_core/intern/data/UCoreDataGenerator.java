package info.u_team.u_team_core.intern.data;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.intern.data.provider.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = UCoreMain.MODID, bus = Bus.MOD)
public class UCoreDataGenerator {
	
	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final DataGenerator generator = event.getGenerator();
		if (event.includeServer()) {
			generator.addProvider(new UCoreRecipesProvider(generator));
		}
		if (event.includeClient()) {
			generator.addProvider(new UCoreLanguagesProvider(generator, UCoreMain.MODID));
		}
	}
}
