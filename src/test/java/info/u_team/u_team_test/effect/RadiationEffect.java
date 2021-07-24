package info.u_team.u_team_test.effect;

import java.util.Random;

import info.u_team.u_team_test.init.TestDamageSources;
import net.minecraft.client.gui.font.glyphs.BakedGlyph.Effect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.world.entity.LivingEntity;

public class RadiationEffect extends Effect {
	
	private final Random random;
	
	public RadiationEffect() {
		super(EffectType.HARMFUL, 0x0B7A14);
		random = new Random();
	}
	
	@Override
	public void performEffect(LivingEntity entityLiving, int amplifier) {
		amplifier += 2;
		entityLiving.attackEntityFrom(TestDamageSources.RADIATION, random.nextInt(amplifier));
		if (entityLiving instanceof PlayerEntity) {
			final PlayerEntity player = (PlayerEntity) entityLiving;
			player.addExhaustion(0.005F * amplifier);
		}
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return random.nextInt(Math.max(20 - (amplifier * 2), 2)) == 0;
	}
}
