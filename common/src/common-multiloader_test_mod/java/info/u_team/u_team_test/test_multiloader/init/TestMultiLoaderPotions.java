package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import info.u_team.u_team_test.test_multiloader.potion.TestPotion;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.alchemy.Potion;

public class TestMultiLoaderPotions {
	
	public static final CommonRegister<Potion> POTIONS = CommonRegister.create(Registries.POTION, TestMultiLoaderReference.MODID);
	
	public static final RegistryEntry<Potion> TEST = POTIONS.register("test", () -> new TestPotion(1200, 0));
	public static final RegistryEntry<Potion> TEST_LONG = POTIONS.register("test_long", () -> new TestPotion(2400, 1));
	public static final RegistryEntry<Potion> TEST_EXTREME = POTIONS.register("test_extreme", () -> new TestPotion(1200, 2));
	
	static void register() {
		POTIONS.register();
	}
	
}
