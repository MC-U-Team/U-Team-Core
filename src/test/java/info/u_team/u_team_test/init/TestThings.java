package info.u_team.u_team_test.init;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_test.TestMod;

@Construct(modid = TestMod.MODID)
public class TestThings implements ModConstruct {
	
	private static Logger LOGGER = LogManager.getLogger();
	
	@Override
	public void construct() {
		testTagAssumptions();
	}
	
	public static void testTagAssumptions() {
		LOGGER.info("Check if tag assumption are working with tag util");
		
		if (TestTags.Blocks.TEST_TAG_2 != TestTags.Blocks.TEST_TAG_2_SAME) {
			throw new IllegalStateException("Two calls with the same tag must return the same tag instance");
		}
		if (TestTags.Blocks.TEST_TAG_2 == TestTags.Blocks.TEST_TAG_2_OPTIONAL) {
			throw new IllegalStateException("First non optional, now optional tag cannot be the same");
		}
	}
}
