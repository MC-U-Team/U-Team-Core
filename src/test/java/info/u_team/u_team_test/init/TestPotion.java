package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.potion.PotionRadiation;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMod.modid, bus = Bus.MOD)
public class TestPotion {
	
	public static final Potion radiation = new PotionRadiation("radiation", 1200, 0);
	public static final Potion radiation_long = new PotionRadiation("radiation_long", 2400, 1);
	public static final Potion radiation_extreme = new PotionRadiation("radiation_extreme", 1200, 2);
	
	@SubscribeEvent
	public static void register(Register<Potion> event) {
		BaseRegistryUtil.getAllRegistryEntriesAndApplyNames(TestMod.modid, Potion.class).forEach(event.getRegistry()::register);
	}
	
}
