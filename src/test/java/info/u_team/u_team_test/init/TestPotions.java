package info.u_team.u_team_test.init;

import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.potion.RadiationPotion;
import net.minecraft.potion.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.*;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestPotions {
	
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, TestMod.MODID);
	
	public static final Potion RADIATION = new RadiationPotion("radiation", 1200, 0);
	public static final Potion RADIATION_LONG = new RadiationPotion("radiation_long", 2400, 1);
	public static final Potion RADIATION_EXTREME = new RadiationPotion("radiation_extreme", 1200, 2);
	
	public static void register(IEventBus bus) {
		POTIONS.register(bus);
	}
	
}
