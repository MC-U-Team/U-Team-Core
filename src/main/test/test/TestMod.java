package test;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.blocks.UBlock;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = "test")
public class TestMod {
	
	private UCreativeTab tab;
	
	private UBlock block;
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		
		UCoreMain.SUBMOD_MODID = "test";
		
		block = new UBlock(Material.ROCK, "testblock", null);
	
		 tab =  new UCreativeTab("test", block, true);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
	}
	
}
