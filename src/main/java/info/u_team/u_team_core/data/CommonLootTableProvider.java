package info.u_team.u_team_core.data;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import info.u_team.u_team_core.intern.loot.SetBlockEntityNBTLootFunction;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator.PathProvider;
import net.minecraft.data.DataGenerator.Target;
import net.minecraft.data.DataProvider;
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
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CommonLootTableProvider implements DataProvider, CommonDataProvider<BiConsumer<ResourceLocation, LootTable>> {
	
	private final GenerationData generationData;
	
	private final PathProvider pathProvider;
	
	public CommonLootTableProvider(GenerationData generationData) {
		this.generationData = generationData;
		
		pathProvider = generationData.generator().createPathProvider(Target.DATA_PACK, "loot_tables");
	}
	
	@Override
	public GenerationData getGenerationData() {
		return generationData;
	}
	
	@Override
	public void run(CachedOutput cache) throws IOException {
		register((location, lootTable) -> {
			CommonDataProvider.saveData(cache, LootTables.serialize(lootTable), pathProvider.json(location), "Could not save loot table");
		});
	}
	
	@Override
	public String getName() {
		return "Loot-Table";
	}
	
	protected static void registerBlock(Supplier<? extends Block> supplier, LootTable lootTable, BiConsumer<ResourceLocation, LootTable> consumer) {
		registerBlock(supplier.get(), lootTable, consumer);
	}
	
	protected static void registerBlock(Block block, LootTable lootTable, BiConsumer<ResourceLocation, LootTable> consumer) {
		final ResourceLocation registryName = ForgeRegistries.BLOCKS.getKey(block);
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
	
	protected static LootTable addBlockEntityBlockLootTable(ItemLike item) {
		return LootTable.lootTable() //
				.setParamSet(LootContextParamSets.BLOCK) //
				.withPool(LootPool.lootPool() //
						.setRolls(ConstantValue.exactly(1)) //
						.add(LootItem.lootTableItem(item)) //
						.apply(SetBlockEntityNBTLootFunction.builder()) //
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
