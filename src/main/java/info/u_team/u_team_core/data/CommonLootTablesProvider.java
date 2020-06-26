package info.u_team.u_team_core.data;

import java.io.IOException;
import java.util.function.*;

import info.u_team.u_team_core.intern.loot.SetTileEntityNBTLootFunction;
import net.minecraft.advancements.criterion.*;
import net.minecraft.block.Block;
import net.minecraft.data.DirectoryCache;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.util.*;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.*;
import net.minecraft.world.storage.loot.functions.*;

public abstract class CommonLootTablesProvider extends CommonProvider {
	
	public CommonLootTablesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void act(DirectoryCache cache) throws IOException {
		registerLootTables((location, lootTable) -> {
			try {
				write(cache, LootTableManager.toJson(lootTable), resolveData(location).resolve("loot_tables").resolve(location.getPath() + ".json"));
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
		return LootTable.builder() //
				.setParameterSet(LootParameterSets.BLOCK) //
				.addLootPool(LootPool.builder() //
						.rolls(ConstantRange.of(1)) //
						.addEntry(ItemLootEntry.builder(item)) //
						.acceptCondition(SurvivesExplosion.builder())) //
				.build();
	}
	
	protected static LootTable addTileEntityBlockLootTable(IItemProvider item) {
		return LootTable.builder() //
				.setParameterSet(LootParameterSets.BLOCK) //
				.addLootPool(LootPool.builder() //
						.rolls(ConstantRange.of(1)) //
						.addEntry(ItemLootEntry.builder(item)) //
						.acceptFunction(SetTileEntityNBTLootFunction.builder()) //
						.acceptCondition(SurvivesExplosion.builder())) //
				.build();
	}
	
	protected static LootTable addFortuneBlockLootTable(Block block, IItemProvider item) {
		return LootTable.builder() //
				.setParameterSet(LootParameterSets.BLOCK) //
				.addLootPool(LootPool.builder() //
						.rolls(ConstantRange.of(1)) //
						.addEntry(ItemLootEntry.builder(block) //
								.acceptCondition(MatchTool.builder(ItemPredicate.Builder.create() //
										.enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))) //
								).alternatively(ItemLootEntry.builder(item) //
										.acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)) //
										.acceptFunction(ExplosionDecay.builder()))))
				.build();
	}
	
}
