package info.u_team.u_team_core;

import org.apache.logging.log4j.*;

import info.u_team.u_team_core.api.IModProxy;
import info.u_team.u_team_core.intern.proxy.*;
import info.u_team.u_team_core.util.verify.JarSignVerifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(UCoreMain.MODID)
public class UCoreMain {
	
	public static final String MODID = "uteamcore";
	public static final Logger LOGGER = LogManager.getLogger("UTeamCore");
	
	private static final IModProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	
	public UCoreMain() {
		JarSignVerifier.checkSigned(MODID);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		PROXY.construct();
	}
	
	@SubscribeEvent
	public void setup(FMLCommonSetupEvent event) {
		PROXY.setup();
	}
	
	@SubscribeEvent
	public void ready(FMLLoadCompleteEvent event) {
		PROXY.complete();
	}
	
}
