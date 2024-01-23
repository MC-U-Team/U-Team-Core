package info.u_team.u_team_test.test_multiloader.neoforge.data.provider;

import java.util.function.BiConsumer;

import info.u_team.u_team_core.data.CommonGlobalLootModifierProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.loot_item_condition.TestEnchantmentLootItemCondition;
import info.u_team.u_team_test.test_multiloader.neoforge.global_loot_modifier.TestLootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

public class NeoForgeTestMultiLoaderGlobalLootModifierProvider extends CommonGlobalLootModifierProvider {
	
	public NeoForgeTestMultiLoaderGlobalLootModifierProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register(BiConsumer<String, ? super IGlobalLootModifier> consumer) {
		consumer.accept("test_enchantment_modifier", new TestLootModifier(TestEnchantmentLootItemCondition.create().build()));
	}
}
