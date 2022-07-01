package info.u_team.u_team_test.data.provider;

import java.util.function.BiConsumer;

import info.u_team.u_team_core.data.CommonLootTableProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.init.TestBlocks;
import info.u_team.u_team_test.init.TestItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

public class TestLootTablesProvider extends CommonLootTableProvider {
	
	public TestLootTablesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register(BiConsumer<ResourceLocation, LootTable> consumer) {
		registerBlock(TestBlocks.BASIC, addFortuneBlockLootTable(TestBlocks.BASIC.get(), TestItems.BASIC.get()), consumer);
		registerBlock(TestBlocks.BASIC_BLOCKENTITY, addTileEntityBlockLootTable(TestBlocks.BASIC_BLOCKENTITY.get()), consumer);
		registerBlock(TestBlocks.BASIC_ENERGY_CREATOR, addTileEntityBlockLootTable(TestBlocks.BASIC_ENERGY_CREATOR.get()), consumer);
		registerBlock(TestBlocks.BASIC_FLUID_INVENTORY, addTileEntityBlockLootTable(TestBlocks.BASIC_FLUID_INVENTORY.get()), consumer);
	}
	
}
