package info.u_team.u_team_core;

import org.apache.logging.log4j.*;

import info.u_team.u_team_core.api.IModProxy;
import info.u_team.u_team_core.intern.proxy.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(UCoreMain.modid)
public class UCoreMain {
	
	public static final String modid = "uteamcore";
	public static final Logger logger = LogManager.getLogger("UTeamCore");
	
	private static final IModProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	
	public UCoreMain() {
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		proxy.construct();
	}
	
	@SubscribeEvent
	public void setup(FMLCommonSetupEvent event) {
		proxy.setup();
	}
	
	@SubscribeEvent
	public void ready(FMLLoadCompleteEvent event) {
		proxy.complete();
	}
	
}
