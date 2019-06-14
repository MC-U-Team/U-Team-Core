package info.u_team.u_team_test.effect;

import java.util.Random;

import info.u_team.u_team_core.effect.UEffect;
import info.u_team.u_team_test.init.TestDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;

public class EffectRadiation extends UEffect {
	
	private final Random random;
	
	public EffectRadiation(String name) {
		super(name, EffectType.HARMFUL, 0x0B7A14);
		random = new Random();
	}
	
	@Override
	public void performEffect(LivingEntity entityLiving, int amplifier) {
		amplifier += 2;
		entityLiving.attackEntityFrom(TestDamageSources.radiation, random.nextInt(amplifier));
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityLiving;
			player.addExhaustion(0.005F * amplifier);
		}
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return random.nextInt(Math.max(20 - (amplifier * 2), 2)) == 0;
	}
}
