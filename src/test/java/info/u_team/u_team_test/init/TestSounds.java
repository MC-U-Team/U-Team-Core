package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import info.u_team.u_team_test.TestMod;
import net.minecraft.util.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class TestSounds {
	
	public static final CommonDeferredRegister<SoundEvent> SOUND_EVENTS = CommonDeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TestMod.MODID);
	
	public static final RegistryObject<SoundEvent> BETTER_ENDERPEARL_USE = SOUND_EVENTS.register("better_enderpearl_use", () -> new SoundEvent(new ResourceLocation(TestMod.MODID, "better_enderpearl_use")));
	
	public static void register(IEventBus bus) {
		SOUND_EVENTS.register(bus);
	}
	
}
