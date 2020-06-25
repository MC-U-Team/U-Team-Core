package info.u_team.u_team_test.init;

import info.u_team.u_team_core.dimension.UModDimension;
import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.dimension.BasicDimension;
import net.minecraft.potion.Effect;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.*;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestModDimensions {
	
	public static final DeferredRegister<ModDimension> MOD_DIMENSIONS = DeferredRegister.create(ForgeRegistries.MOD_DIMENSIONS, TestMod.MODID);
	
	public static final ModDimension BASIC = new UModDimension("basic", BasicDimension::new);
	
	public static void register(IEventBus bus) {
		MOD_DIMENSIONS.register(bus);
	}
	
//	@SubscribeEvent
//	public static void register(Register<ModDimension> event) {
//		BaseRegistryUtil.getAllRegistryEntriesAndApplyNames(TestMod.MODID, ModDimension.class).forEach(event.getRegistry()::register);
//	}
//	
}
