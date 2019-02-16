package info.u_team.u_team_core;

import info.u_team.u_team_core.intern.proxy.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("uteamcore")
public class UCoreMain {
	
	public UCoreMain() {
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}
	
	@SubscribeEvent
	public void common(FMLCommonSetupEvent event) {
		CommonProxy.setup();
	}
	
	@SubscribeEvent
	public void client(final FMLClientSetupEvent event) {
		ClientProxy.setup();
	}
	
}
