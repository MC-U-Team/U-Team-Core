package info.u_team.u_team_core;

import org.apache.logging.log4j.*;

import info.u_team.u_team_core.util.verify.JarSignVerifier;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(UCoreMain.MODID)
public class UCoreMain {
	
	public static final String MODID = "uteamcore";
	public static final Logger LOGGER = LogManager.getLogger("UTeamCore");
	
	public UCoreMain() {
		JarSignVerifier.checkSigned(MODID);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}
}
