package info.u_team.u_team_core.util.registry;

import info.u_team.u_team_core.util.stack.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.*;
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
	
	public <T extends Entity> void registerEntityRenderer(Class<T> clazz, IRenderFactory<? super T> rendererFactory) {
		RenderingRegistry.registerEntityRenderingHandler(clazz, rendererFactory);
	}
	
	public void registerKeybinding(KeyBinding key) {
		net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding(key);
	}
	
	public void registerModel(Item item) {
		registerModel(item, 0);
	}
	
	public void registerModel(Block block) {
		registerModel(block, 0);
	}
	
	public void registerModel(Item item, int meta) {
		registerModel(item, meta, new ModelResourceLocation(ItemUtil.getRegistryName(item), "inventory"));
	}
	
	public void registerModel(Block block, int meta) {
		registerModel(block, meta, new ModelResourceLocation(BlockUtil.getRegistryName(block), "inventory"));
	}
	
	public void registerModel(Item item, int meta, ModelResourceLocation location) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, location);
	}
	
	public void registerModel(Block block, int meta, ModelResourceLocation location) {
		registerModel(Item.getItemFromBlock(block), meta, location);
	}
	
	public void registerModelVariants(Item item, String... names) {
		ResourceLocation[] res = new ResourceLocation[names.length];
		String modid = item.getRegistryName().getResourceDomain();
		for (int i = 0; i < names.length; i++) {
			res[i] = new ResourceLocation(modid, names[i]);
		}
		ModelBakery.registerItemVariants(item, res);
	}
	
	public <T extends TileEntity> void registerSpecialTileEntityRenderer(Class<T> clazz, TileEntitySpecialRenderer<? super T> renderer) {
		net.minecraftforge.fml.client.registry.ClientRegistry.bindTileEntitySpecialRenderer(clazz, renderer);
	}
	
}
