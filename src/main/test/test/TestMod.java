package test;

import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.sub.USub;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = "test")
public class TestMod {
	
	private UCreativeTab tab;
	
	private UBlock block;
	private UItem item;
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		
		USub.setID("test");
		
		block = new UBlock(Material.ROCK, "testblock");
		tab = new UCreativeTab("test", block, true);
		
		item = new UItem("test", tab);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
	}
	
}
