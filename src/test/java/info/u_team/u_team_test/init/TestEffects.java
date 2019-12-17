package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.effect.RadiationEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestEffects {
	
	public static final Effect RADIATION = new RadiationEffect("radiation");
	
	@SubscribeEvent
	public static void register(Register<Effect> event) {
		BaseRegistryUtil.getAllRegistryEntriesAndApplyNames(TestMod.MODID, Effect.class).forEach(event.getRegistry()::register);
	}
	
}
