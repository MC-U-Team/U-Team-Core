package test;

import info.u_team.u_team_core.block.*;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.generation.GeneratableRegistry;
import info.u_team.u_team_core.generation.ore.*;
import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.sub.USubMod;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = "test", name = "test", version = "1.0.0")
public class TestMod extends USubMod {
	
	public TestMod() {
		super("test", "TestMod", "1.0.0");
	}
	
	public static UCreativeTab tab;
	
	public static UBlock block;
	public static UItem item;
	
	public static UBlockTileEntity blocktile;
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		super.preinit(event);
		tab = new UCreativeTab("test");
		
		item = new UItem("testitem", tab);
		block = new UBlock(Material.ROCK, "testblock", tab);
		tab.setIcon(block);
		
		blocktile = new UBlockTileEntity(UTileEntity.class, "testtile", Material.ROCK, "testtile", tab);
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		GeneratableOre ore1 = new GeneratableOre(Blocks.LAPIS_ORE.getDefaultState(), new GenerationOreCenterSpread(Blocks.AIR, 7, 1, 170, 16));
		
		GeneratableOre ore2 = new GeneratableOre(Blocks.DIAMOND_ORE.getDefaultState(), new GenerationOreMinMax(Blocks.AIR, 8, 1, 120, 136));
		
		GeneratableRegistry.addFirst(0, ore1);
		GeneratableRegistry.addLast(0, ore2);
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
	}
	
	@EventHandler
	
}
