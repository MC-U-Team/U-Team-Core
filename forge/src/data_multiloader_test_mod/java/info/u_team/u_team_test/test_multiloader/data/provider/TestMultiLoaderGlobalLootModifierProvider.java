package info.u_team.u_team_test.test_multiloader.data.provider;

import java.util.function.BiConsumer;

import info.u_team.u_team_core.data.CommonGlobalLootModifierProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.global_loot_modifier.TestLootModifier;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderEnchantments;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.common.loot.IGlobalLootModifier;

public class TestMultiLoaderGlobalLootModifierProvider extends CommonGlobalLootModifierProvider {
	
	public TestMultiLoaderGlobalLootModifierProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register(BiConsumer<String, ? super IGlobalLootModifier> consumer) {
		consumer.accept("test", new TestLootModifier(new LootItemCondition[] { MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(TestMultiLoaderEnchantments.TEST.get(), MinMaxBounds.Ints.atLeast(1)))).build() }));
	}
}
