package info.u_team.u_team_test.init;

import info.u_team.u_team_core.dimension.UModDimension;
import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.dimension.BasicDimension;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestModDimensions {
	
	public static final ModDimension BASIC = new UModDimension("basic", BasicDimension::new);
	
	@SubscribeEvent
	public static void register(Register<ModDimension> event) {
		BaseRegistryUtil.getAllRegistryEntriesAndApplyNames(TestMod.MODID, ModDimension.class).forEach(event.getRegistry()::register);
	}
	
}
