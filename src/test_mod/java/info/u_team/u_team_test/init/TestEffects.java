package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.effect.RadiationEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class TestEffects {
	
	public static final CommonDeferredRegister<Effect> EFFECTS = CommonDeferredRegister.create(ForgeRegistries.POTIONS, TestMod.MODID);
	
	public static final RegistryObject<Effect> RADIATION = EFFECTS.register("radiation", RadiationEffect::new);
	
	public static void registerMod(IEventBus bus) {
		EFFECTS.register(bus);
	}
	
}
