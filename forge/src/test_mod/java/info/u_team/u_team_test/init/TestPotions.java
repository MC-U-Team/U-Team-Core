package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.potion.RadiationPotion;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TestPotions {
	
	public static final CommonDeferredRegister<Potion> POTIONS = CommonDeferredRegister.create(ForgeRegistries.POTIONS, TestMod.MODID);
	
	public static final RegistryObject<Potion> RADIATION = POTIONS.register("radiation", () -> new RadiationPotion(1200, 0));
	public static final RegistryObject<Potion> RADIATION_LONG = POTIONS.register("radiation_long", () -> new RadiationPotion(2400, 1));
	public static final RegistryObject<Potion> RADIATION_EXTREME = POTIONS.register("radiation_extreme", () -> new RadiationPotion(1200, 2));
	
	public static void registerMod(IEventBus bus) {
		POTIONS.register(bus);
	}
	
}
