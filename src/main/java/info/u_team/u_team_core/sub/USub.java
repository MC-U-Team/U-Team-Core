package info.u_team.u_team_core.sub;

import info.u_team.u_team_core.UCoreConstants;

/**
 * Holds the current sub modid
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 *
 */
public class USub {
	
	private static String SUB_MODID = UCoreConstants.MODID;
	
	public static String getID() {
		return SUB_MODID;
	}
	
	static void setID(String id) {
		SUB_MODID = id;
	}
	
}
