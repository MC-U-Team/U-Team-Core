package info.u_team.u_team_core;

import org.apache.logging.log4j.*;

import info.u_team.u_team_core.intern.config.ClientConfig;
import info.u_team.u_team_core.util.verify.JarSignVerifier;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;

@Mod(UCoreMod.MODID)
public class UCoreMod {
	
	public static final String MODID = "uteamcore";
	public static final Logger LOGGER = LogManager.getLogger("UTeamCore");
	
	public UCoreMod() {
		JarSignVerifier.checkSigned(MODID);
		ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfig.CONFIG);
		
	}
}
