package info.u_team.u_team_test.test_multiloader.forge.data.provider;

import info.u_team.u_team_core.data.CommonGlobalLootModifierProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.forge.global_loot_modifier.TestLootModifier;
import info.u_team.u_team_test.test_multiloader.loot_item_condition.TestEnchantmentLootItemCondition;

public class ForgeTestMultiLoaderGlobalLootModifierProvider extends CommonGlobalLootModifierProvider {
	
	public ForgeTestMultiLoaderGlobalLootModifierProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register(GlobalLootModifierRegister register) {
		register.register("test_enchantment_modifier", new TestLootModifier(TestEnchantmentLootItemCondition.create(register.registries()).build()));
	}
}
