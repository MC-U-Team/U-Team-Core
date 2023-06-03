package info.u_team.u_team_test.test_multiloader.init.fabric;

import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderEnchantments;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

public class TestMultiLoaderFabricLootTableModifications {
	
	private static void modifyLootTable(ResourceManager resourceManager, LootTables lootManager, ResourceLocation id, LootTable.Builder tableBuilder, LootTableSource source) {
		tableBuilder.apply(SmeltItemFunction.smelted().when(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(TestMultiLoaderEnchantments.TEST.get(), MinMaxBounds.Ints.atLeast(1))))));
	}
	
	static void register() {
		LootTableEvents.MODIFY.register(TestMultiLoaderFabricLootTableModifications::modifyLootTable);
	}
	
}
