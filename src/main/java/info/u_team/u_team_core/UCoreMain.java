package info.u_team.u_team_core;

import org.apache.logging.log4j.*;

import info.u_team.u_team_core.intern.config.ClientConfig;
import info.u_team.u_team_core.intern.proxy.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("uteamcore")
public class UCoreMain {
	
	public static final Logger logger = LogManager.getLogger("UTeamCore");
	
	public UCoreMain() {
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfig.config);
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
