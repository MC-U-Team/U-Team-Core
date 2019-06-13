package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.PotionRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.potion.PotionRadiation;

public class TestPotion {
	
	public static final PotionRadiation radiation = new PotionRadiation("radiation", 1200, 0);
	public static final PotionRadiation radiation_long = new PotionRadiation("radiation_long", 2400, 1);
	public static final PotionRadiation radiation_extreme = new PotionRadiation("radiation_extreme", 1200, 2);
	
	public static void construct() {
		PotionRegistry.register(TestMod.modid, TestPotion.class);
	}
	
}
