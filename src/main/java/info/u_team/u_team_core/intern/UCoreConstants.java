package info.u_team.u_team_core.intern;

import org.apache.logging.log4j.*;

/**
 * This class defines important constants
 * 
 * @author HyCraftHD
 * @date 16.08.2017
 *
 */
public final class UCoreConstants {
	
	public static final String MODID = "uteamcore";
	public static final String NAME = "UTeam Core";
	public static final String VERSION = "1.1.1";
	public static final String MCVERSION = "1.8.9";
	
	public static final String COMMONPROXY = "info.u_team.u_team_core.intern.proxy.CommonProxy";
	public static final String CLIENTPROXY = "info.u_team.u_team_core.intern.proxy.ClientProxy";
	
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	
	private UCoreConstants() { // Let this class never be initialized
	}
	
}
