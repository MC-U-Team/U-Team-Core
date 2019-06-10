package info.u_team.u_team_test.potiontype;

import info.u_team.u_team_core.effect.UEffect;
import info.u_team.u_team_test.init.TestPotions;
import net.minecraft.potion.PotionEffect;

public class PotionTypeRadiation extends UEffect {
	
	public PotionTypeRadiation(String name, int duration, int amplifier) {
		super(name, "radiation", new PotionEffect(TestPotions.radiation, duration, amplifier));
	}
	
}
