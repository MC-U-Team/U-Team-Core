package info.u_team.u_team_test.test_multiloader.potion;

import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class TestPotion extends Potion {
	
	public TestPotion(int duration, int amplifier) {
		// TODO bad supplier here
		super("test", new MobEffectInstance(TestMultiLoaderMobEffects.TEST.get(), duration, amplifier));
	}
}
