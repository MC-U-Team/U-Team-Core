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
	
	public static <T extends TileEntity> void registerSpecialTileEntityRenderer(Class<T> clazz, TileEntityRenderer<? super T> renderer) {
		net.minecraftforge.fml.client.registry.ClientRegistry.bindTileEntitySpecialRenderer(clazz, renderer);
	}
	
	public static void registerKeybinding(KeyBinding key) {
		net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding(key);
	}
	
}
