package info.u_team.u_team_test.potion;

import info.u_team.u_team_test.init.TestMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class RadiationPotion extends Potion {
	
	public RadiationPotion(int duration, int amplifier) {
		super("radiation", new MobEffectInstance(TestMobEffects.RADIATION.get(), duration, amplifier));
	}
}
