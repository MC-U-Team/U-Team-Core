package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import info.u_team.u_team_test.TestMod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class TestSounds {
	
	public static final CommonDeferredRegister<SoundEvent> SOUND_EVENTS = CommonDeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TestMod.MODID);
	
	public static final RegistryObject<SoundEvent> BETTER_ENDERPEARL_USE = SOUND_EVENTS.register("better_enderpearl_use", SoundEvent::new);
	
	public static void registerMod(IEventBus bus) {
		SOUND_EVENTS.register(bus);
	}
	
}
