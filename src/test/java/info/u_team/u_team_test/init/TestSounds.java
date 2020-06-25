package info.u_team.u_team_test.init;

import info.u_team.u_team_core.soundevent.USoundEvent;
import info.u_team.u_team_test.TestMod;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.*;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestSounds {
	
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TestMod.MODID);
	
	public static final SoundEvent BETTER_ENDERPEARL_USE = new USoundEvent("better_enderpearl_use");
	
	public static void register(IEventBus bus) {
		SOUND_EVENTS.register(bus);
	}
	
}
