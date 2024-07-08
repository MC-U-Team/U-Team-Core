package info.u_team.u_team_test.test_multiloader.loot_item_condition;

import java.util.List;

import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderEnchantments;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemEnchantmentsPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicates;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

public class TestEnchantmentLootItemCondition {
	
	public static LootItemCondition.Builder create(HolderLookup.Provider registries) {
		return MatchTool.toolMatches(ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(TestMultiLoaderEnchantments.TEST), MinMaxBounds.Ints.atLeast(1))))));
	}
	
}
