package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.potion.RadiationPotion;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.alchemy.Potion;

public class TestPotions {
	
	public static final CommonRegister<Potion> POTIONS = CommonRegister.create(Registries.POTION, TestMod.MODID);
	
	public static final RegistryEntry<Potion> RADIATION = POTIONS.register("radiation", () -> new RadiationPotion(1200, 0));
	public static final RegistryEntry<Potion> RADIATION_LONG = POTIONS.register("radiation_long", () -> new RadiationPotion(2400, 1));
	public static final RegistryEntry<Potion> RADIATION_EXTREME = POTIONS.register("radiation_extreme", () -> new RadiationPotion(1200, 2));
	
	public static void register() {
		POTIONS.register();
	}
	
}
