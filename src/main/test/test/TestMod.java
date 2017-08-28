package test;

import info.u_team.u_team_core.block.*;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.sub.USub;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = "test")
public class TestMod {
	
	private UCreativeTab tab;
	
	private UBlock block;
	private UItem item;
	
	private UBlockTileEntity blocktile;
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		
		USub.setID("test");
		
		block = new UBlock(Material.ROCK, "testblock");
		tab = new UCreativeTab("test", block, true);
		
		item = new UItem("test", tab);
		
		blocktile = new UBlockTileEntity(UTileEntity.class,"lelll", Material.ROCK, "testtile");
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
	}
	
}
