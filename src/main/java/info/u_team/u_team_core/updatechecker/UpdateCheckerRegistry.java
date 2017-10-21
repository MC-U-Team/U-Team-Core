package info.u_team.u_team_core.updatechecker;

import info.u_team.u_team_core.intern.updatechecker.UpdateChecker;

/**
 * Update API<br>
 * -> Update checker registry
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 *
 */
public class UpdateCheckerRegistry {
	
	public static UpdateChecker checker = new UpdateChecker();
	
	public static void addMod(String modid, String url) { // Should be done in preinit
		checker.addMod(modid, url);
	}
	
	public static void getResult(String modid) { // Only usable after init int postinit
		checker.getResult(modid);
	}
	
}
