package info.u_team.u_team_test.init;

import info.u_team.u_team_core.registry.BlockRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.block.BlockBasic;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class TestBlocks {
	
	public static BlockBasic basic = new BlockBasic("basicblock");
	
	public static Block block = new Block(Block.Properties.create(Material.BARRIER)).setRegistryName(TestMod.modid, "block");
	
	public static void construct() {
		BlockRegistry.register(TestMod.modid, RegistryUtil.getRegistryEntries(Block.class, TestBlocks.class));
	}
	
}
