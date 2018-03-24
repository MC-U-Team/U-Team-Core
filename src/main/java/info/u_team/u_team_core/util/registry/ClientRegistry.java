package info.u_team.u_team_core.util.registry;

import info.u_team.u_team_core.util.stack.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.*;

/**
 * Client registry
 * 
 * @author HyCraftHD
 * @date 23.10.2017
 *
 */
@SideOnly(Side.CLIENT)
public class ClientRegistry {
	
	public static void registerEntityRenderingHandler(Class<? extends Entity> clazz, Render<? extends Entity> renderer) {
		RenderingRegistry.registerEntityRenderingHandler(clazz, renderer);
	}
	
	public static void registerKeybinding(KeyBinding key) {
		net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding(key);
	}
	
	public static void registerModel(Item item) {
		registerModel(item, 0);
	}
	
	public static void registerModel(Block block) {
		registerModel(block, 0);
	}
	
	public static void registerModel(Item item, int meta) {
		registerModel(item, meta, new ModelResourceLocation(ItemUtil.getRegistryName(item), "inventory"));
	}
	
	public static void registerModel(Block block, int meta) {
		registerModel(block, meta, new ModelResourceLocation(BlockUtil.getRegistryName(block), "inventory"));
	}
	
	public static void registerModel(Item item, int meta, ModelResourceLocation location) {
		ModelLoader.setCustomModelResourceLocation(item, meta, location);
	}
	
	public static void registerModel(Block block, int meta, ModelResourceLocation location) {
		registerModel(Item.getItemFromBlock(block), meta, location);
	}
	
	public static <T extends TileEntity> void registerSpecialTileEntityRenderer(Class<T> clazz, TileEntitySpecialRenderer<T> renderer) {
		net.minecraftforge.fml.client.registry.ClientRegistry.bindTileEntitySpecialRenderer(clazz, renderer);
	}
	
}
