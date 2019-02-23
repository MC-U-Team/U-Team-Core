package info.u_team.u_team_test.proxy;

import info.u_team.u_team_core.api.IModProxy;
import info.u_team.u_team_test.init.*;

public class CommonProxy implements IModProxy {
	
	@Override
	public void construct() {
		TestItems.construct();
		TestBlocks.construct();
		TestTileEntityTypes.construct();
		TestEntityTypes.construct();
		TestSounds.construct();
		TestEnchantments.construct();
		TestPotions.construct();
		TestPotionTypes.construct();
	}
	
	@Override
	public void setup() {
		TestItemGroups.setup();
	}
	
	@Override
	public void complete() {
	}
	
}
