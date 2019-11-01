package info.u_team.u_team_test.data.provider;

import info.u_team.u_team_core.data.CommonBlockStatesProvider;
import info.u_team.u_team_test.init.TestBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class TestBlockStatesProvider extends CommonBlockStatesProvider {
	
	public TestBlockStatesProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
	}
	
	@Override
	protected void registerStatesAndModels() {
		simpleBlock(TestBlocks.BASIC);
		simpleBlock(TestBlocks.BASIC_ENERGY_CREATOR);
		facingBlock(TestBlocks.BASIC_TILEENTITY, cubeFacing(TestBlocks.BASIC_TILEENTITY.getRegistryName().getPath(), modLoc("block/tileentity"), modLoc("block/tileentity_front")));
	}
}
