package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import info.u_team.u_team_test.test_multiloader.loot_item_function.TestLootItemFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class TestMultiLoaderLootItemFunctions {
	
	public static final CommonRegister<LootItemFunctionType<?>> LOOT_ITEM_FUNCTIONS = CommonRegister.create(Registries.LOOT_FUNCTION_TYPE, TestMultiLoaderReference.MODID);
	
	public static final RegistryEntry<LootItemFunctionType<TestLootItemFunction>> TEST = LOOT_ITEM_FUNCTIONS.register("test", () -> new LootItemFunctionType<>(TestLootItemFunction.CODEC));
	
	static void register() {
		LOOT_ITEM_FUNCTIONS.register();
	}
	
}
