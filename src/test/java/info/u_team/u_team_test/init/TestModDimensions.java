package info.u_team.u_team_test.init;

import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.dimension.BasicDimension;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.*;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestModDimensions {
	
	public static final DeferredRegister<ModDimension> MOD_DIMENSIONS = DeferredRegister.create(ForgeRegistries.MOD_DIMENSIONS, TestMod.MODID);
	
	public static final RegistryObject<ModDimension> BASIC = MOD_DIMENSIONS.register("basic", () -> ModDimension.withFactory(BasicDimension::new));
	
	public static void register(IEventBus bus) {
		MOD_DIMENSIONS.register(bus);
	}
}
