package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.event.RegisterMenuScreensEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class UCorePostMenuScreensEvent {
	
	private static void setup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ModLoader.get().postEvent(new RegisterMenuScreensEvent());
		});
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(UCorePostMenuScreensEvent::setup);
	}
	
}
