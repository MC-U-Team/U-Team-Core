package info.u_team.u_team_test.potion;

import info.u_team.u_team_core.potion.UPotion;
import info.u_team.u_team_test.init.TestEffects;
import net.minecraft.potion.*;

public class RadiationPotion extends UPotion {
	
	public RadiationPotion(String name, int duration, int amplifier) {
		super(name, "radiation", new EffectInstance(TestEffects.radiation, duration, amplifier));
	}
}
