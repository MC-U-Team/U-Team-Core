package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.*;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.effect.EffectRadiation;
import net.minecraft.potion.Effect;

public class TestEffects {
	
	public static final Effect radiation = new EffectRadiation("radiation");
	
	public static void construct() {
		EffectRegistry.register(TestMod.modid, TestEffects.class);
	}
	
}
