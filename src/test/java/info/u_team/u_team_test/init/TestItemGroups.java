package info.u_team.u_team_test.init;

import info.u_team.u_team_core.itemgroup.UItemGroup;
import info.u_team.u_team_test.TestMod;

public class TestItemGroups {
	
	public static final UItemGroup group = new UItemGroup(TestMod.modid, "group");
	
	public static void setup() {
		group.setIcon(TestBlocks.basic);
	}
	
}
