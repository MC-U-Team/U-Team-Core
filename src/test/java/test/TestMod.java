package test;

import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.entity.UEntityEntry;
import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.registry.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@Mod(modid = "test", name = "TestMod", version = "1.0.0", updateJSON = "http://files.minecraftforge.net/maven/net/minecraftforge/forge/promotions_slim.json")
public class TestMod {
	
	public static final UCreativeTab tab = new UCreativeTab("test", "tab");
	
	public static final UBlock block = new UBlock("testblock", Material.ROCK, tab);
	
	public static final UItem item = new UItem("testitem", tab);
	
	public static final UEntityEntry entity = new UEntityEntry(EntityEntryBuilder.create().entity(EntityTest.class).id(new ResourceLocation("test", "testentityrofl"), 0).name("testentity").egg(0xFFFFFF, 0xAAAAAA).tracker(64, 20, false));
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		tab.setIcon(block);
		BlockRegistry.register("test", block);
		ItemRegistry.register("test", item);
		EntityRegistry.register("test", entity);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
	}
	
}
