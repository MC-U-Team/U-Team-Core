package info.u_team.u_team_core.data;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import info.u_team.u_team_core.intern.loot.SetTileEntityNBTLootFunction;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public abstract class CommonLootTablesProvider extends CommonProvider {

	public CommonLootTablesProvider(GenerationData data) {
		super(data);
	}

	@Override
	public void run(HashCache cache) throws IOException {
		registerLootTables((location, lootTable) -> {
			try {
				write(cache, LootTables.serialize(lootTable), resolveData(location).resolve("loot_tables").resolve(location.getPath() + ".json"));
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
		final var registryName = block.getRegistryName();
		consumer.accept(new ResourceLocation(registryName.getNamespace(), "blocks/" + registryName.getPath()), lootTable);
	}

	protected static LootTable addBasicBlockLootTable(ItemLike item) {
		return LootTable.lootTable() //
				.setParamSet(LootContextParamSets.BLOCK) //
				.withPool(LootPool.lootPool() //
						.setRolls(ConstantValue.exactly(1)) //
						.add(LootItem.lootTableItem(item)) //
						.when(ExplosionCondition.survivesExplosion())) //
				.build();
	}

	protected static LootTable addTileEntityBlockLootTable(ItemLike item) {
		return LootTable.lootTable() //
				.setParamSet(LootContextParamSets.BLOCK) //
				.withPool(LootPool.lootPool() //
						.setRolls(ConstantValue.exactly(1)) //
						.add(LootItem.lootTableItem(item)) //
						.apply(SetTileEntityNBTLootFunction.builder()) //
						.when(ExplosionCondition.survivesExplosion())) //
				.build();
	}

	protected static LootTable addFortuneBlockLootTable(Block block, ItemLike item) {
		return LootTable.lootTable() //
				.setParamSet(LootContextParamSets.BLOCK) //
				.withPool(LootPool.lootPool() //
						.setRolls(ConstantValue.exactly(1)) //
						.add(LootItem.lootTableItem(block) //
								.when(MatchTool.toolMatches(ItemPredicate.Builder.item() //
										.hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))) //
								).otherwise(LootItem.lootTableItem(item) //
										.apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)) //
										.apply(ApplyExplosionDecay.explosionDecay()))))
				.build();
	}

}
