package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.BlockRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.block.*;

public class TestBlocks {
	
	public static final BlockBasic basic = new BlockBasic("basicblock");
	
	public static final BlockTileEntity tileentity = new BlockTileEntity("tileentity");
	
	public static void construct() {
		BlockRegistry.register(TestMod.modid, TestBlocks.class);
	}
	
}
