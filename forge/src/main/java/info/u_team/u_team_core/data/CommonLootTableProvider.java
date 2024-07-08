package info.u_team.u_team_core.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import info.u_team.u_team_core.data.CommonLootTableProvider.LootTableRegister;
import info.u_team.u_team_core.intern.loot_item_function.SetBlockEntityNBTLootItemFunction;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemEnchantmentsPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicates;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
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

public abstract class CommonLootTableProvider implements DataProvider, CommonDataProvider<LootTableRegister> {
	
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
		return withRegistries(registries -> {
			final List<CompletableFuture<?>> futures = new ArrayList<>();
			register(new LootTableRegister() {
				
				@Override
				public HolderLookup.Provider registries() {
					return registries;
				}
				
				@Override
				public void register(ResourceLocation location, LootTable lootTable) {
					futures.add(saveData(cache, registries, LootTable.DIRECT_CODEC, lootTable, pathProvider.json(location)));
				}
			});
			return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
		});
	}
	
	@Override
	public String getName() {
		return "Loot-Table: " + nameSuffix();
	}
	
	protected static void registerBlock(Supplier<? extends Block> supplier, LootTable.Builder builder, LootTableRegister register) {
		registerBlock(supplier.get(), builder, register);
	}
	
	protected static void registerBlock(Block block, LootTable.Builder builder, LootTableRegister register) {
		final ResourceLocation location = RegistryUtil.getBuiltInRegistry(Registries.BLOCK).getKey(block).withPrefix("blocks/");
		register.register(location, builder.setRandomSequence(location).build());
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
	
	protected static LootTable.Builder addFortuneBlockLootTable(HolderLookup.Provider registries, Block block, ItemLike item) {
		return LootTable.lootTable() //
				.setParamSet(LootContextParamSets.BLOCK) //
				.withPool(LootPool.lootPool() //
						.setRolls(ConstantValue.exactly(1)) //
						.add(LootItem.lootTableItem(block) //
								.when(MatchTool.toolMatches(ItemPredicate.Builder.item() //
										.withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1)))))) //
								).otherwise(LootItem.lootTableItem(item) //
										.apply(ApplyBonusCount.addOreBonusCount(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE))) //
										.apply(ApplyExplosionDecay.explosionDecay()))));
	}
	
	public static interface LootTableRegister {
		
		void register(ResourceLocation location, LootTable lootTable);
		
		HolderLookup.Provider registries();
	}
	
}