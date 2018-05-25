package test;

import info.u_team.u_team_core.block.*;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.generation.GeneratableRegistry;
import info.u_team.u_team_core.generation.ore.*;
import info.u_team.u_team_core.generation.schematic.*;
import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.sub.USubMod;
import info.u_team.u_team_core.util.registry.ClientRegistry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
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
		// block = new UBlock(Material.ROCK, "testblock", tab);
		tab.setIcon(block);
		
		// blocktile = new UBlockTileEntity(UTileEntity.class, "testtile", Material.ROCK, "testtile", tab);
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		// Noside
		
		ClientRegistry.registerModel(item, 0, new ModelResourceLocation(new ResourceLocation("stick"), "inventory"));
		ClientRegistry.registerModel(block, 0, new ModelResourceLocation(new ResourceLocation("stone"), "inventory"));
		ClientRegistry.registerModel(blocktile, 0, new ModelResourceLocation(new ResourceLocation("bedrock"), "inventory"));
		
		GeneratableOre ore1 = new GeneratableOre(Blocks.LAPIS_ORE.getDefaultState(), new GenerationOreCenterSpread(Blocks.AIR, 7, 1, 170, 16));
		
		GeneratableOre ore2 = new GeneratableOre(Blocks.DIAMOND_ORE.getDefaultState(), new GenerationOreMinMax(Blocks.AIR, 8, 1, 120, 136));
		
		GeneratableSchematic schematic = new GeneratableSchematic(new GenerationSchematicSurfaceChunk(this.getClass().getResource("/test.uschematic"), 1));
		
		GeneratableRegistry.addFirst(0, ore1);
		GeneratableRegistry.addLast(0, ore2);
		GeneratableRegistry.addLast(0, schematic);
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
	}
	
	@EventHandler
	public void server(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandBase() {
			
			@Override
			public String getUsage(ICommandSender sender) {
				return "Usage";
			}
			
			@Override
			public String getName() {
				return "test";
			}
			
			@Override
			public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
				
				World world = server.worlds[0];
				EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
				if (player == null) {
					throw new CommandException("Only players can use this command!");
				}
				
				BlockPos pos = player.getPosition();
				
				// world.markBlockRangeForRenderUpdate(pos.subtract(new BlockPos(30, 30, 30)), pos.add(new BlockPos(30, 30, 30)));
				//
				// System.out.println("marked");
				
				// if (args.length == 1) {
				// int count = 0;
				// try {
				// count = Integer.valueOf(args[0]);
				// } catch (Exception ex) {
				// throw new CommandException("Arg 1 must be an int!", ex);
				// }
				// if (count == 0) {
				// throw new CommandException("Arg 1 must be > 0");
				// }
				//
				// BlockPos pos = player.getPosition();
				//
				// USchematicSaveRegion region = new USchematicSaveRegion(world, pos.add(-count, 0, -count), pos.add(count, 0, count));
				// try {
				// USchematicWriter saver = new USchematicWriter(region, new File("savefile.nbt"));
				// saver.finished((success, time) -> System.out.println("No error: " + success + " - in " + time + " ms")).start();
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
				//
				// } else if (args.length == 2) {
				// boolean center = false, rotate = false;
				// try {
				// center = Boolean.valueOf(args[0]);
				// rotate = Boolean.valueOf(args[1]);
				// } catch (Exception ex) {
				// throw new CommandException("Arg 1 and Arg 2 must be an boolean!", ex);
				// }
				//
				// BlockPos pos = player.getPosition();
				//
				// USchematicLoadRegion region = new USchematicLoadRegion(world, pos).center().rotate(USchematicRotation.ROTATION_270);
				// try {
				// USchematicReader reader = new USchematicReader(region, new File("savefile.nbt"));
				// reader.finished((success, time) -> System.out.println("No error: " + success + " - in " + time + " ms")).start();
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
				// }
				
			}
		});
	}
	
}
