package info.u_team.u_team_test.test_multiloader.loot_item_condition;

import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderEnchantments;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

public class TestEnchantmentLootItemCondition {
	
	public static LootItemCondition.Builder create() {
		return MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(TestMultiLoaderEnchantments.TEST.get(), MinMaxBounds.Ints.atLeast(1))));
	}
	
}
