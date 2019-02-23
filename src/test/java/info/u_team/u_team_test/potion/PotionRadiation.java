package info.u_team.u_team_test.potion;

import java.util.Random;

import info.u_team.u_team_core.potion.UPotion;
import info.u_team.u_team_test.init.TestDamageSources;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class PotionRadiation extends UPotion {
	
	private final Random random;
	
	public PotionRadiation(String name) {
		super(name, true, 0x0B7A14, 256);
		random = new Random();
	}
	
	@Override
	public void performEffect(EntityLivingBase entityliving, int amplifier) {
		amplifier += 2;
		entityliving.attackEntityFrom(TestDamageSources.radiation, random.nextInt(amplifier));
		if (entityliving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityliving;
			player.addExhaustion(0.005F * amplifier);
		}
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return random.nextInt(Math.max(20 - (amplifier * 2), 2)) == 0;
	}
}
