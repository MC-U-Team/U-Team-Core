package info.u_team.u_team_test.init;

import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.effect.RadiationEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.*;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestEffects {
	
	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, TestMod.MODID);
	
	public static final RegistryObject<Effect> RADIATION = EFFECTS.register("radiation", () -> new RadiationEffect("radiation"));
	
	public static void register(IEventBus bus) {
		EFFECTS.register(bus);
	}
	
}
