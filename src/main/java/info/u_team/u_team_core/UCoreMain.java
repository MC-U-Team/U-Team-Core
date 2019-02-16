package info.u_team.u_team_core;

import info.u_team.u_team_core.intern.proxy.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.*;

@Mod("uteamcore")
@EventBusSubscriber(bus = Bus.MOD)
public class UCoreMain {
	
	@SubscribeEvent
	private void common(FMLCommonSetupEvent event) {
		CommonProxy.setup();
	}
	
	@SubscribeEvent
	private void client(final FMLClientSetupEvent event) {
		ClientProxy.setup();
	}
	
	
}
