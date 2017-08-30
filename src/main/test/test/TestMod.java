package test;

import java.util.Random;

import info.u_team.u_team_core.block.*;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.generation.ore.*;
import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.sub.USubMod;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		
		GeneratableOre ore1 = new GeneratableOre(block.getDefaultState(), new GenerationOreMinMax(Blocks.AIR, 1, 100, 100));
		
		GeneratableOre ore2 = new GeneratableOre(block.getDefaultState(), new GenerationOreMinMax(Blocks.AIR, 3, 120, 130));
		
		GameRegistry.registerWorldGenerator(new IWorldGenerator() {
			
			@Override
			public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
				if (world.provider.getDimension() == 0) {
					
					BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
					
					ore1.generate(world, random, pos);
					ore2.generate(world, random, pos);
				}
			}
		}, 0);
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
	}
	
}
