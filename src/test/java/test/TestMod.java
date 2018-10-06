package test;

import info.u_team.u_team_core.api.IMetaType;
import info.u_team.u_team_core.block.*;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.entity.UEntityEntry;
import info.u_team.u_team_core.item.*;
import info.u_team.u_team_core.registry.*;
import info.u_team.u_team_core.util.EnumHelperSoundCategory;
import net.minecraft.block.material.Material;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@Mod(modid = "test", name = "TestMod", version = "1.0.0", updateJSON = "http://files.minecraftforge.net/maven/net/minecraftforge/forge/promotions_slim.json")
public class TestMod {
	
	public static final UCreativeTab tab = new UCreativeTab("test", "tab");
	
	public static final UBlock block = new UBlock("testblock", Material.ROCK, tab);
	
	public static final UItem item = new UItem("testitem", tab);
	
	public static final UEntityEntry entity = new UEntityEntry(EntityEntryBuilder.create().entity(EntityTest.class).id(new ResourceLocation("test", "testentity"), 0).name("testentity").egg(0xFFFFFF, 0xAAAAAA).tracker(64, 20, false));
	
	public static final UItemMetaData itemmeta = new UItemMetaData("testitemmeta", tab, EnumTest.values());
	
	public static final UBlockMetaData blockmeta = new UBlockMetaData("testblockmeta", Material.ROCK, tab, EnumTest.values());
	
	public static final SoundCategory category = EnumHelperSoundCategory.addSoundCategory("test");
	
	public static enum EnumTest implements IMetaType {
		
		FIRST(0, "first"),
		SECOND(1, "second"),
		THIRD(2, "third");
		
		private int meta;
		private String name;
		
		private EnumTest(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public int getMetadata() {
			return meta;
		}
		
	}
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		tab.setIcon(block);
		BlockRegistry.register("test", block);
		ItemRegistry.register("test", item);
		EntityRegistry.register("test", entity);
		
		ItemRegistry.register("test", itemmeta);
		BlockRegistry.register("test", blockmeta);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
	}
	
	@EventHandler
	public void postinit(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandBase() {
			
			@Override
			public String getUsage(ICommandSender sender) {
				return "/test";
			}
			
			@Override
			public String getName() {
				return "test";
			}
			
			@Override
			public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
				if (sender instanceof EntityPlayerMP) {
					EntityPlayerMP player = (EntityPlayerMP) sender;
					System.out.println("test");
					player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.BLOCK_ANVIL_BREAK, category, 1.0F, 1.0F);
				}
			}
		});
	}
	
}
