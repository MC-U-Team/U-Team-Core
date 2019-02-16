package info.u_team.u_team_core.registry;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.client.registry.*;

@OnlyIn(Dist.CLIENT)
public class ClientRegistry {
	
	public static <T extends Entity> void registerEntityRenderer(Class<T> clazz, IRenderFactory<? super T> rendererFactory) {
		RenderingRegistry.registerEntityRenderingHandler(clazz, rendererFactory);
	}
	
	public static void registerKeybinding(KeyBinding key) {
		net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding(key);
	}
	
	// public static void registerModel(Item item) {
	// registerModel(item, 0);
	// }
	//
	// public static void registerModel(Block block) {
	// registerModel(block, 0);
	// }
	//
	// public static void registerModel(Item item, int meta) {
	// registerModel(item, meta, new ModelResourceLocation(item.getRegistryName(),
	// "inventory"));
	// }
	//
	// public static void registerModel(Block block, int meta) {
	// registerModel(block, meta, new ModelResourceLocation(block.getRegistryName(),
	// "inventory"));
	// }
	//
	// public static void registerModel(Item item, int meta, ModelResourceLocation
	// location) {
	// ModelLoader.setCustomModelResourceLocation(item, meta, location);
	// }
	//
	// public static void registerModel(Block block, int meta, ModelResourceLocation
	// location) {
	// registerModel(Item.getItemFromBlock(block), meta, location);
	// }
	
	public static <T extends TileEntity> void registerSpecialTileEntityRenderer(Class<T> clazz, TileEntityRenderer<? super T> renderer) {
		net.minecraftforge.fml.client.registry.ClientRegistry.bindTileEntitySpecialRenderer(clazz, renderer);
	}
	
}
