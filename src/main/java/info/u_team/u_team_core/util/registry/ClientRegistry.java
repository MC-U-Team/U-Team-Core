package info.u_team.u_team_core.util.registry;

import java.util.function.*;

import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.*;
import net.minecraft.inventory.container.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.client.registry.*;

@OnlyIn(Dist.CLIENT)
public class ClientRegistry {
	
	public static <M extends Container, U extends Screen & IHasContainer<M>> void registerScreen(Supplier<? extends ContainerType<? extends M>> supplier, ScreenManager.IScreenFactory<M, U> factory) {
		ScreenManager.registerFactory(supplier.get(), factory);
	}
	
	public static <T extends Entity> void registerEntityRenderer(Supplier<? extends EntityType<T>> supplier, IRenderFactory<? super T> rendererFactory) {
		RenderingRegistry.registerEntityRenderingHandler(supplier.get(), rendererFactory);
	}
	
	public static <T extends TileEntity> void registerSpecialTileEntityRenderer(Supplier<? extends TileEntityType<T>> supplier, TileEntityRenderer<? super T> renderer) {
		registerSpecialTileEntityRenderer(supplier, dispatcher -> renderer);
	}
	
	public static <T extends TileEntity> void registerSpecialTileEntityRenderer(Supplier<? extends TileEntityType<T>> supplier, Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super T>> rendererFactory) {
		net.minecraftforge.fml.client.registry.ClientRegistry.bindTileEntityRenderer(supplier.get(), rendererFactory);
	}
	
	public static void registerKeybinding(KeyBinding key) {
		net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding(key);
	}
	
}
