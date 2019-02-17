package info.u_team.u_team_core;

import org.apache.logging.log4j.*;

import info.u_team.u_team_core.intern.proxy.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(UCoreMain.modid)
public class UCoreMain {
	
	public static final String modid = "uteamcore";
	
	public static final Logger logger = LogManager.getLogger("UTeamCore");
	
	public UCoreMain() {
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		CommonProxy.construct();
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
