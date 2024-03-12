package info.u_team.u_team_test.potion;

import info.u_team.u_team_test.init.TestEffects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;

public class RadiationPotion extends Potion {
	
	public RadiationPotion(int duration, int amplifier) {
		super("radiation", new EffectInstance(TestEffects.RADIATION.get(), duration, amplifier));
	}
}
