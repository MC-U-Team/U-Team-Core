package info.u_team.u_team_test.init;

import info.u_team.u_team_core.registry.BlockRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.block.*;
import net.minecraft.block.Block;

public class TestBlocks {
	
	public static BlockBasic basic = new BlockBasic("basicblock");
	
	public static BlockTileEntity tileentity = new BlockTileEntity("tileentity");
	
	public static void construct() {
		BlockRegistry.register(TestMod.modid, RegistryUtil.getRegistryEntries(Block.class, TestBlocks.class));
	}
	
}
