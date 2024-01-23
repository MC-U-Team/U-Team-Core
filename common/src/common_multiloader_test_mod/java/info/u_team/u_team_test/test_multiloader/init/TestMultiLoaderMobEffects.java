package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import info.u_team.u_team_test.test_multiloader.effect.TestEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;

public class TestMultiLoaderMobEffects {
	
	public static final CommonRegister<MobEffect> MOB_EFFECTS = CommonRegister.create(Registries.MOB_EFFECT, TestMultiLoaderReference.MODID);
	
	public static final RegistryEntry<MobEffect> TEST = MOB_EFFECTS.register("test", TestEffect::new);
	
	static void register() {
		MOB_EFFECTS.register();
	}
	
}
