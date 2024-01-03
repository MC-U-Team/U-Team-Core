package info.u_team.u_team_core.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import info.u_team.u_team_core.intern.loot_item_function.SetBlockEntityNBTLootItemFunction;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput.PathProvider;
import net.minecraft.data.PackOutput.Target;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public abstract class CommonLootTableProvider implements DataProvider, CommonDataProvider<BiConsumer<ResourceLocation, LootTable>> {
	
	private final GenerationData generationData;
	
	private final PathProvider pathProvider;
	
	public CommonLootTableProvider(GenerationData generationData) {
		this.generationData = generationData;
		
		pathProvider = generationData.output().createPathProvider(Target.DATA_PACK, "loot_tables");
	}
	
	@Override
	public GenerationData getGenerationData() {
		return generationData;
	}
	
	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		final List<CompletableFuture<?>> futures = new ArrayList<>();
		register((location, lootTable) -> {
			futures.add(CommonDataProvider.saveData(cache, LootTable.CODEC, lootTable, pathProvider.json(location)));
		});
		return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
	}
	
	@Override
	public String getName() {
		return "Loot-Table";
	}
	
	protected static void registerBlock(Supplier<? extends Block> supplier, LootTable.Builder builder, BiConsumer<ResourceLocation, LootTable> consumer) {
		registerBlock(supplier.get(), builder, consumer);
	}
	
	protected static void registerBlock(Block block, LootTable.Builder builder, BiConsumer<ResourceLocation, LootTable> consumer) {
		final ResourceLocation location = RegistryUtil.getBuiltInRegistry(Registries.BLOCK).getKey(block).withPrefix("blocks/");
		consumer.accept(location, builder.setRandomSequence(location).build());
	}
	
	protected static LootTable.Builder addBasicBlockLootTable(ItemLike item) {
		return LootTable.lootTable() //
				.setParamSet(LootContextParamSets.BLOCK) //
				.withPool(LootPool.lootPool() //
						.setRolls(ConstantValue.exactly(1)) //
						.add(LootItem.lootTableItem(item)) //
						.when(ExplosionCondition.survivesExplosion()));
	}
	
	protected static LootTable.Builder addBlockEntityBlockLootTable(ItemLike item) {
		return LootTable.lootTable() //
				.setParamSet(LootContextParamSets.BLOCK) //
				.withPool(LootPool.lootPool() //
						.setRolls(ConstantValue.exactly(1)) //
						.add(LootItem.lootTableItem(item)) //
						.apply(SetBlockEntityNBTLootItemFunction.builder()) //
						.when(ExplosionCondition.survivesExplosion()));
	}
	
	protected static LootTable.Builder addFortuneBlockLootTable(Block block, ItemLike item) {
		return LootTable.lootTable() //
				.setParamSet(LootContextParamSets.BLOCK) //
				.withPool(LootPool.lootPool() //
						.setRolls(ConstantValue.exactly(1)) //
						.add(LootItem.lootTableItem(block) //
								.when(MatchTool.toolMatches(ItemPredicate.Builder.item() //
										.hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))) //
								).otherwise(LootItem.lootTableItem(item) //
										.apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)) //
										.apply(ApplyExplosionDecay.explosionDecay()))));
	}
	
}
