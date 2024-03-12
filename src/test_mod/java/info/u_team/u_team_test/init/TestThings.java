package info.u_team.u_team_test.init;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.util.registry.BlockRegistryObject;
import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.block.BasicBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Construct(modid = TestMod.MODID)
public class TestThings implements ModConstruct {
	
	private static Logger LOGGER = LogManager.getLogger();
	
	@Override
	public void construct() {
		testTagAssumptions();
		
		BusRegister.registerMod(bus -> bus.addListener(TestThings::setup));
	}
	
	public static void testTagAssumptions() {
		LOGGER.info("Check if tag assumption are working with tag util");
		
		if ((TestTags.Blocks.TEST_TAG_2 != TestTags.Blocks.TEST_TAG_2_SAME) || (TestTags.Blocks.TEST_TAG_2 != TestTags.Blocks.TEST_TAG_2_OPTIONAL)) {
			throw new IllegalStateException("Two calls with the same tag must return the same tag instance");
		}
	}
	
	private static void setup(FMLCommonSetupEvent event) {
		final BlockRegistryObject<BasicBlock, BlockItem> registryObject = TestBlocks.BASIC;
		
		if (!registryObject.getItem().getRegistryName().equals(registryObject.getItemId())) {
			throw new IllegalStateException("Registry name of item must be set and match the expected one");
		}
		
		LOGGER.info("Block: {} and item {}", registryObject.get(), registryObject.getItem());
	}
}
