package info.u_team.u_team_core.updatechecker;

import info.u_team.u_team_core.intern.updatechecker.UpdateChecker;

/**
 * Update API<br>
 * -> Update checker registry
 * 
 * Jsonformat: <code>{ "mcversion":{ "version": "newest mod version", "download": "Downloadlink" } }</code>
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 *
 */
public class UpdateCheckerRegistry {
	
	private static UpdateChecker checker = new UpdateChecker();
	
	public static void addMod(String modid, String url) { // Should be done in preinit
		checker.addMod(modid, url);
	}
	
	public static void getResult(String modid) { // Only usable after init or postinit
		checker.getResult(modid);
	}
	
	public static UpdateChecker getChecker() {
		return checker;
	}
	
}
