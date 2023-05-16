package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.TestMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

public class TestSoundEvents {
	
	public static final CommonRegister<SoundEvent> SOUND_EVENTS = CommonRegister.create(Registries.SOUND_EVENT, TestMod.MODID);
	
	public static final RegistryEntry<SoundEvent> BETTER_ENDERPEARL_USE = SOUND_EVENTS.register("better_enderpearl_use", SoundEvent::createVariableRangeEvent);
	
	public static void register() {
		SOUND_EVENTS.register();
	}
	
}
