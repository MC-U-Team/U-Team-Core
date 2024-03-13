package info.u_team.u_team_test.data.provider;

import java.util.function.BiConsumer;

import info.u_team.u_team_core.data.*;
import info.u_team.u_team_test.init.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;

public class TestLootTablesProvider extends CommonLootTablesProvider {
	
	public TestLootTablesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerLootTables(BiConsumer<ResourceLocation, LootTable> consumer) {
		registerBlock(TestBlocks.BASIC, addFortuneBlockLootTable(TestBlocks.BASIC.get(), TestItems.BASIC.get()), consumer);
		registerBlock(TestBlocks.BASIC_TILEENTITY, addTileEntityBlockLootTable(TestBlocks.BASIC_TILEENTITY.get()), consumer);
		registerBlock(TestBlocks.BASIC_ENERGY_CREATOR, addTileEntityBlockLootTable(TestBlocks.BASIC_ENERGY_CREATOR.get()), consumer);
		registerBlock(TestBlocks.BASIC_FLUID_INVENTORY, addTileEntityBlockLootTable(TestBlocks.BASIC_FLUID_INVENTORY.get()), consumer);
	}
	
}
