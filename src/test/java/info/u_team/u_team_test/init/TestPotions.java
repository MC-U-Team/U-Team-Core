package info.u_team.u_team_test.init;

import info.u_team.u_team_core.registry.PotionRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.potion.PotionRadiation;
import net.minecraft.potion.Potion;

public class TestPotions {
	
	public static final Potion radiation = new PotionRadiation("radiation");
	
	public static void construct() {
		PotionRegistry.register(TestMod.modid, TestPotions.class);
	}
	
}
