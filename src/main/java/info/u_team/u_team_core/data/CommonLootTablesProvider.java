package info.u_team.u_team_core.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.BiConsumer;

import net.minecraft.advancements.criterion.*;
import net.minecraft.block.Block;
import net.minecraft.data.*;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.util.*;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.*;
import net.minecraft.world.storage.loot.functions.*;

public abstract class CommonLootTablesProvider extends CommonProvider {
	
	public CommonLootTablesProvider(String name, DataGenerator generator) {
		super(name, generator);
	}
	
	@Override
	public void act(DirectoryCache cache) throws IOException {
	}
	
	protected abstract void registerLootTables(BiConsumer<ResourceLocation, LootTable> consumer);
	
	@Override
	protected Path resolvePath(Path outputFolder) {
		return outputFolder.resolve("data");
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
	
	protected static LootTable addFortuneBlockLootTable(Block block, IItemProvider item) {
		return LootTable.builder() //
				.setParameterSet(LootParameterSets.BLOCK) //
				.addLootPool(LootPool.builder() //
						.rolls(ConstantRange.of(1)) //
						.addEntry(ItemLootEntry.builder(block) //
								.acceptCondition(MatchTool.builder(ItemPredicate.Builder.create() //
										.enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))) //
								).func_216080_a(ItemLootEntry.builder(item) //
										.acceptFunction(ApplyBonus.func_215869_a(Enchantments.FORTUNE)) //
										.acceptFunction(ExplosionDecay.func_215863_b()))))
				.build();
	}
	
}
