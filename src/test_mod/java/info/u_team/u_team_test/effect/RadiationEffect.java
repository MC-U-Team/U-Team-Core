package info.u_team.u_team_test.effect;

import java.util.Random;

import info.u_team.u_team_test.init.TestDamageSources;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class RadiationEffect extends MobEffect {
	
	private final Random random;
	
	public RadiationEffect() {
		super(MobEffectCategory.HARMFUL, 0x0B7A14);
		random = new Random();
	}
	
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		amplifier += 2;
		entity.hurt(new DamageSource(TestDamageSources.RADIATION.getHolder().get()), random.nextInt(amplifier));
		if (entity instanceof final Player player) {
			player.causeFoodExhaustion(0.005F * amplifier);
		}
	}
	
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return random.nextInt(Math.max(20 - (amplifier * 2), 2)) == 0;
	}
}
