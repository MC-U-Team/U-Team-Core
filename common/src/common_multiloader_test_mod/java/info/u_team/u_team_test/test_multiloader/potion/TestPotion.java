package info.u_team.u_team_test.test_multiloader.potion;

import java.util.List;

import info.u_team.u_team_core.potion.UPotion;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderMobEffects;
import net.minecraft.world.effect.MobEffectInstance;

public class TestPotion extends UPotion {
	
	public TestPotion(int duration, int amplifier) {
		super("test", () -> List.of(new MobEffectInstance(TestMultiLoaderMobEffects.TEST.getHolder(), duration, amplifier)));
	}
}
