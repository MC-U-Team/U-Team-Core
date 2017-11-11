package test;

import info.u_team.u_team_core.block.*;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.generation.GeneratableRegistry;
import info.u_team.u_team_core.generation.ore.*;
import info.u_team.u_team_core.generation.schematic.*;
import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.sub.USubMod;
import info.u_team.u_team_core.tileentity.UTileEntity;
import info.u_team.u_team_core.util.registry.ClientRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
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
		block = new UBlock(Material.rock, "testblock", tab);
		tab.setIcon(block);
		
		blocktile = new UBlockTileEntity(UTileEntity.class, "testtile", Material.rock, "testtile", tab);
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		// Noside
		
		ClientRegistry.registerModel(item, 0, new ModelResourceLocation(new ResourceLocation("stick"), "inventory"));
		ClientRegistry.registerModel(block, 0, new ModelResourceLocation(new ResourceLocation("stone"), "inventory"));
		ClientRegistry.registerModel(blocktile, 0, new ModelResourceLocation(new ResourceLocation("bedrock"), "inventory"));
		
		GeneratableOre ore1 = new GeneratableOre(Blocks.lapis_ore.getDefaultState(), new GenerationOreCenterSpread(Blocks.air, 7, 1, 170, 16));
		
		GeneratableOre ore2 = new GeneratableOre(Blocks.diamond_ore.getDefaultState(), new GenerationOreMinMax(Blocks.air, 8, 1, 120, 136));
		
		GeneratableSchematic schematic = new GeneratableSchematic(new GenerationSchematicSurfaceChunk(this.getClass().getResource("/test.uschematic"), 1));
		
		GeneratableRegistry.addFirst(0, ore1);
		GeneratableRegistry.addLast(0, ore2);
		GeneratableRegistry.addLast(0, schematic);
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
	}
	
}
