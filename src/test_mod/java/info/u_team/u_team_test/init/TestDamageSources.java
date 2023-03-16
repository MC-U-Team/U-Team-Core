package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import info.u_team.u_team_test.TestMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class TestDamageSources {
	
	public static final CommonDeferredRegister<DamageType> DAMAGE_TYPES = CommonDeferredRegister.create(Registries.DAMAGE_TYPE, TestMod.MODID);
	
	public static final RegistryObject<DamageType> RADIATION = DAMAGE_TYPES.register("radiation", () -> new DamageType("radiation", 0));
	
	public static void registerMod(IEventBus bus) {
		DAMAGE_TYPES.register(bus);
	}
	
	// public static final DamageSource RADIATION = new DamageSource("radiation").bypassArmor().setMagic();
	
}
