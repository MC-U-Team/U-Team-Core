package test;

import info.u_team.u_team_core.block.*;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.item.tool.UItemSword;
import info.u_team.u_team_core.sub.USubMod;
import info.u_team.u_team_core.util.registry.ClientRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.command.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = "test", name = "TestMod", version = "1.0.0", updateJSON = "http://files.minecraftforge.net/maven/net/minecraftforge/forge/promotions_slim.json")
public class TestMod extends USubMod {
	
	public static UCreativeTab tab;
	
	public static UBlock block;
	public static UItem item;
	
	public static UItemSword sword;
	
	public static UBlockTileEntity blocktile;
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		super.preinit(event);
		tab = new UCreativeTab("test");
		
		item = new UItem("testitem", tab);
		block = new UBlock("testblock", Material.iron);
		tab.setIcon(block);
		
		sword = new UItemSword("test", EnumHelper.addToolMaterial("test", 1, 22, 2, 55, 10));
		// blocktile = new UBlockTileEntity(UTileEntity.class, "testtile",
		// Material.ROCK, "testtile", tab);
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		// Noside
		
		ClientRegistry.registerModel(item, 0, new ModelResourceLocation(new ResourceLocation("stick"), "inventory"));
		ClientRegistry.registerModel(sword, 0, new ModelResourceLocation(new ResourceLocation("diamond"), "inventory"));
		
		ClientRegistry.registerModel(block, 0, new ModelResourceLocation(new ResourceLocation("stone"), "inventory"));
		// ClientRegistry.registerModel(blocktile, 0, new ModelResourceLocation(new
		// ResourceLocation("bedrock"), "inventory"));
		//
		// GeneratableOre ore1 = new GeneratableOre(Blocks.lapis_ore.getDefaultState(),
		// new GenerationOreCenterSpread(Blocks.air, 7, 1, 170, 16));
		//
		// GeneratableOre ore2 = new
		// GeneratableOre(Blocks.diamond_ore.getDefaultState(), new
		// GenerationOreMinMax(Blocks.air, 8, 1, 120, 136));
		//
		// GeneratableSchematic schematic = new GeneratableSchematic(new
		// GenerationSchematicSurfaceChunk(this.getClass().getResource("/test.uschematic"),
		// 1));
		//
		// GeneratableRegistry.addFirst(0, ore1);
		// GeneratableRegistry.addLast(0, ore2);
		// GeneratableRegistry.addLast(0, schematic);
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
			public void processCommand(ICommandSender sender, String[] args) throws CommandException {
				
				// World world = server.worldServers[0];
				// EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
				// if (player == null) {
				// throw new CommandException("Only players can use this command!");
				// }
				//
				// BlockPos pos = player.getPosition();
				
				// world.markBlockRangeForRenderUpdate(pos.subtract(new BlockPos(30, 30, 30)),
				// pos.add(new BlockPos(30, 30, 30)));
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
				// USchematicSaveRegion region = new USchematicSaveRegion(world, pos.add(-count,
				// 0, -count), pos.add(count, 0, count));
				// try {
				// USchematicWriter saver = new USchematicWriter(region, new
				// File("savefile.nbt"));
				// saver.finished((success, time) -> System.out.println("No error: " + success +
				// " - in " + time + " ms")).start();
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
				// USchematicLoadRegion region = new USchematicLoadRegion(world,
				// pos).center().rotate(USchematicRotation.ROTATION_270);
				// try {
				// USchematicReader reader = new USchematicReader(region, new
				// File("savefile.nbt"));
				// reader.finished((success, time) -> System.out.println("No error: " + success
				// + " - in " + time + " ms")).start();
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
				// }
				
			}
		});
	}
	
}
