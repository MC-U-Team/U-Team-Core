package info.u_team.u_team_test.proxy;

import info.u_team.u_team_test.init.*;

public class CommonProxy {
	
	public static void construct() {
		TestItems.construct();
		TestBlocks.construct();
	}
	
	public static void setup() {
		TestItemGroups.setup();
	}
	
}
