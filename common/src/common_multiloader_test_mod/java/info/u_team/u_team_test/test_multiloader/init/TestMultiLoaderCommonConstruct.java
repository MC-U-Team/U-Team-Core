package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;

@Construct(modid = TestMultiLoaderReference.MODID)
public class TestMultiLoaderCommonConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		TestMultiLoaderArmorMaterials.register();
		TestMultiLoaderBlockEntityTypes.register();
		TestMultiLoaderBlocks.register();
		TestMultiLoaderCreativeTabs.register();
		TestMultiLoaderEntityTypes.register();
		TestMultiLoaderItems.register();
		TestMultiLoaderLootItemFunctions.register();
		TestMultiLoaderMenuTypes.register();
		TestMultiLoaderMobEffects.register();
		TestMultiLoaderPotions.register();
		TestMultiLoaderSoundEvents.register();
		
		TestMultiLoaderCommands.register();
		TestMultiLoaderCommonEvents.register();
		TestMultiLoaderEntityAttributes.register();
		TestMultiLoaderGameRules.register();
		TestMultiLoaderNetwork.register();
		TestMultiLoaderSpawnPlacements.register();
	}
	
}
