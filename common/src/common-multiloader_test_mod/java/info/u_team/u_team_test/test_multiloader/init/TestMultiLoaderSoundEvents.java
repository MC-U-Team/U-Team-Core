package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

public class TestMultiLoaderSoundEvents {
	
	public static final CommonRegister<SoundEvent> SOUND_EVENTS = CommonRegister.create(Registries.SOUND_EVENT, TestMultiLoaderReference.MODID);
	
	public static final RegistryEntry<SoundEvent> TEST_ENDERPEARL_USE = SOUND_EVENTS.register("test_enderpearl_use", SoundEvent::createVariableRangeEvent);
	
	static void register() {
		SOUND_EVENTS.register();
	}
	
}
