package test;

import info.u_team.u_team_core.block.*;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.generation.GeneratableRegistry;
import info.u_team.u_team_core.generation.ore.*;
import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.schematic.SchematicEntry;
import info.u_team.u_team_core.sub.USubMod;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
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
			
			@SuppressWarnings("deprecation")
			@Override
			public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
				
				World world = server.worldServers[0];
				EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
				if (player == null) {
					throw new CommandException("Only players can use this command!");
				}
				
				BlockPos pos = player.getPosition();
				
				if (args.length == 0) {
					SchematicEntry entry = new SchematicEntry("furnace");
					entry.toBlock(world, pos);
				} else {
					int count = 0;
					try {
						count = Integer.valueOf(args[0]);
					} catch (Exception ex) {
						throw new CommandException("Arg 1 must be an int!", ex);
					}
					if (count == 0) {
						throw new CommandException("Arg 1 must be > 0");
					}
					
					BlockPos pos1 = pos.add(count, 0, count);
					BlockPos pos2 = pos.add(-count, 0, -count);
					
					StructureBoundingBox box = new StructureBoundingBox(pos1, pos2);
					
					BlockPos min = new BlockPos(box.minX, box.minY, box.minZ);
					BlockPos max = new BlockPos(box.maxX, box.maxY, box.maxZ);
					
					SchematicEntry entry = new SchematicEntry("stone", 2);
					
					for (int x = min.getX(); x <= max.getX(); x++) {
						for (int z = min.getZ(); z <= max.getZ(); z++) {
							for (int y = min.getY(); y <= max.getY(); y++) {
								BlockPos nowpos = new BlockPos(x, y, z);
								entry.toBlock(world, nowpos);
							}
						}
					}
					
					world.markBlockRangeForRenderUpdate(min, max);
				}
				
			}
		});
	}
	
}
