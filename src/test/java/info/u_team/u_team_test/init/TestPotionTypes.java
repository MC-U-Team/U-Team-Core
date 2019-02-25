package info.u_team.u_team_test.init;

import info.u_team.u_team_core.registry.PotionTypeRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.potiontype.PotionTypeRadiation;
import net.minecraft.potion.PotionType;

public class TestPotionTypes {
	
	public static final PotionType radiation = new PotionTypeRadiation("radiation", 1200, 0);
	public static final PotionType radiation_long = new PotionTypeRadiation("radiation_long", 2400, 1);
	public static final PotionType radiation_extreme = new PotionTypeRadiation("radiation_extreme", 1200, 2);
	
	public static void construct() {
		PotionTypeRegistry.register(TestMod.modid, TestPotionTypes.class);
	}
	
}
