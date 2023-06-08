package info.u_team.u_team_test.test_multiloader.init.fabric;

import info.u_team.u_team_test.test_multiloader.loot_item_condition.TestEnchantmentLootItemCondition;
import info.u_team.u_team_test.test_multiloader.loot_item_function.TestLootItemFunction;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootTable;

public class TestMultiLoaderFabricLootTableModifications {
	
	private static void modifyLootTable(ResourceManager resourceManager, LootDataManager lootManager, ResourceLocation id, LootTable.Builder tableBuilder, LootTableSource source) {
		tableBuilder.apply(TestLootItemFunction.builder().when(TestEnchantmentLootItemCondition.create()));
	}
	
	static void register() {
		LootTableEvents.MODIFY.register(TestMultiLoaderFabricLootTableModifications::modifyLootTable);
	}
	
}
