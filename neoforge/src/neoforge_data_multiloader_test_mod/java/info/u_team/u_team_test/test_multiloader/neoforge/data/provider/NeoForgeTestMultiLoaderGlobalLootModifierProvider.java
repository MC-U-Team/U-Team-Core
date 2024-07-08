package info.u_team.u_team_test.test_multiloader.neoforge.data.provider;

import info.u_team.u_team_core.data.CommonGlobalLootModifierProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.loot_item_condition.TestEnchantmentLootItemCondition;
import info.u_team.u_team_test.test_multiloader.neoforge.global_loot_modifier.TestLootModifier;
import net.neoforged.neoforge.common.conditions.WithConditions;

public class NeoForgeTestMultiLoaderGlobalLootModifierProvider extends CommonGlobalLootModifierProvider {
	
	public NeoForgeTestMultiLoaderGlobalLootModifierProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register(GlobalLootModifierRegister register) {
		register.register("test_enchantment_modifier", new WithConditions<>(new TestLootModifier(TestEnchantmentLootItemCondition.create(register.registries()).build())));
	}
	
}
