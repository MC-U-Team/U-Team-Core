package info.u_team.u_team_core.data;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import info.u_team.u_team_core.intern.loot.SetTileEntityNBTLootFunction;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.DirectoryCache;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public abstract class CommonLootTablesProvider extends CommonProvider {
	
	public CommonLootTablesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void run(DirectoryCache cache) throws IOException {
		registerLootTables((location, lootTable) -> {
			try {
				write(cache, LootTableManager.serialize(lootTable), resolveData(location).resolve("loot_tables").resolve(location.getPath() + ".json"));
			} catch (final IOException ex) {
				LOGGER.error(marker, "Could not write data.", ex);
			}
		});
	}
	
	protected abstract void registerLootTables(BiConsumer<ResourceLocation, LootTable> consumer);
	
	@Override
	public String getName() {
		return "Loot-Tables";
	}
	
	protected static void registerBlock(Supplier<? extends Block> supplier, LootTable lootTable, BiConsumer<ResourceLocation, LootTable> consumer) {
		registerBlock(supplier.get(), lootTable, consumer);
	}
	
	protected static void registerBlock(Block block, LootTable lootTable, BiConsumer<ResourceLocation, LootTable> consumer) {
		final ResourceLocation registryName = block.getRegistryName();
		consumer.accept(new ResourceLocation(registryName.getNamespace(), "blocks/" + registryName.getPath()), lootTable);
	}
	
	protected static LootTable addBasicBlockLootTable(IItemProvider item) {
		return LootTable.lootTable() //
				.setParamSet(LootParameterSets.BLOCK) //
				.withPool(LootPool.lootPool() //
						.setRolls(ConstantRange.exactly(1)) //
						.add(ItemLootEntry.lootTableItem(item)) //
						.when(SurvivesExplosion.survivesExplosion())) //
				.build();
	}
	
	protected static LootTable addTileEntityBlockLootTable(IItemProvider item) {
		return LootTable.lootTable() //
				.setParamSet(LootParameterSets.BLOCK) //
				.withPool(LootPool.lootPool() //
						.setRolls(ConstantRange.exactly(1)) //
						.add(ItemLootEntry.lootTableItem(item)) //
						.apply(SetTileEntityNBTLootFunction.builder()) //
						.when(SurvivesExplosion.survivesExplosion())) //
				.build();
	}
	
	protected static LootTable addFortuneBlockLootTable(Block block, IItemProvider item) {
		return LootTable.lootTable() //
				.setParamSet(LootParameterSets.BLOCK) //
				.withPool(LootPool.lootPool() //
						.setRolls(ConstantRange.exactly(1)) //
						.add(ItemLootEntry.lootTableItem(block) //
								.when(MatchTool.toolMatches(ItemPredicate.Builder.item() //
										.hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))) //
								).otherwise(ItemLootEntry.lootTableItem(item) //
										.apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE)) //
										.apply(ExplosionDecay.explosionDecay()))))
				.build();
	}
	
}
