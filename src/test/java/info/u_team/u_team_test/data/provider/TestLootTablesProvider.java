package info.u_team.u_team_test.data.provider;

import java.util.function.BiConsumer;

import info.u_team.u_team_core.data.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;

public class TestLootTablesProvider extends CommonLootTablesProvider {

	public TestLootTablesProvider(GenerationData data) {
		super(data);
	}

	@Override
	protected void registerLootTables(BiConsumer<ResourceLocation, LootTable> consumer) {
	}
	
}
