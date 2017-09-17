package test;

import java.io.*;

import info.u_team.u_team_core.block.*;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.generation.GeneratableRegistry;
import info.u_team.u_team_core.generation.ore.*;
import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.schematic.*;
import info.u_team.u_team_core.sub.USubMod;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
	public void server(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandBase() {
			
			@Override
			public String getCommandUsage(ICommandSender sender) {
				return "Usage";
			}
			
			@Override
			public String getCommandName() {
				return "test";
			}
			
			@Override
			public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
				
				World world = server.worldServers[0];
				EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
				if (player == null) {
					throw new CommandException("Only players can use this command!");
				}
				
				if (args.length == 1) {
					int count = 0;
					try {
						count = Integer.valueOf(args[0]);
					} catch (Exception ex) {
						throw new CommandException("Arg 1 must be an int!", ex);
					}
					if (count == 0) {
						throw new CommandException("Arg 1 must be > 0");
					}
					
					BlockPos pos = player.getPosition();
					
					SchematicRegion region = new SchematicRegion(pos.add(-count, 0, -count), pos.add(count, 0, count));

					System.out.println(region.getCount());
					System.out.println(region.getMin());
					System.out.println(region.getMax());
					
					try {
						File file = new File("hello.txt");
						file.createNewFile();
						
						SchematicSaver saver = new SchematicSaver(world, region, new FileOutputStream(file));
						saver.finished(success -> {System.out.println("Schematic generated with " + success);});
						
						saver.start();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
			}
		});
	}
	
}
