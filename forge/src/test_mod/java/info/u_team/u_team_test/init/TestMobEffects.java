package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.effect.RadiationEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;

public class TestMobEffects {
	
	public static final CommonRegister<MobEffect> MOB_EFFECTS = CommonRegister.create(Registries.MOB_EFFECT, TestMod.MODID);
	
	public static final RegistryEntry<MobEffect> RADIATION = MOB_EFFECTS.register("radiation", RadiationEffect::new);
	
	public static void register() {
		MOB_EFFECTS.register();
	}
	
}
