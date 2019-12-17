package info.u_team.u_team_test.init;

import info.u_team.u_team_core.soundevent.USoundEvent;
import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import info.u_team.u_team_test.TestMod;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestSounds {
	
	public static final SoundEvent BETTER_ENDERPEARL_USE = new USoundEvent("better_enderpearl_use");
	
	@SubscribeEvent
	public static void register(Register<SoundEvent> event) {
		BaseRegistryUtil.getAllRegistryEntriesAndApplyNames(TestMod.MODID, SoundEvent.class).forEach(event.getRegistry()::register);
	}
	
}
