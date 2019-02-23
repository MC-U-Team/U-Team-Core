package info.u_team.u_team_test.init;

import info.u_team.u_team_core.damagesource.UDamageSource;
import net.minecraft.util.DamageSource;

public class TestDamageSources {
	
	public static final DamageSource radiation = new UDamageSource("radiation").setDamageBypassesArmor().setMagicDamage();
	
}
