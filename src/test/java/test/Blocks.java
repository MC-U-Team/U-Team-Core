package test;

import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import net.minecraft.block.material.Material;

public class Blocks {

	public static final UCreativeTab tab = new UCreativeTab("test");

	public static final UBlock block = new UBlock("testblock", Material.IRON, tab);

	static void init() {
		System.out.println("_------------------------------------------------");
		tab.setIcon(block);
		System.out.println(block.getRegistryName());
		System.out.println(block.getUnlocalizedName());
	}

}
