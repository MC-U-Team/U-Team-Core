package info.u_team.u_team_core.util.registry;

import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientRegistry {
	
	public static <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void registerScreen(Supplier<? extends MenuType<? extends M>> supplier, MenuScreens.ScreenConstructor<M, U> factory) {
		MenuScreens.register(supplier.get(), factory);
	}
	
	public static <T extends Entity> void registerEntityRenderer(Supplier<? extends EntityType<T>> supplier, IRenderFactory<? super T> rendererFactory) {
		RenderingRegistry.registerEntityRenderingHandler(supplier.get(), rendererFactory);
	}
	
	public static <T extends BlockEntity> void registerSpecialTileEntityRenderer(Supplier<? extends BlockEntityType<T>> supplier, BlockEntityRenderer<? super T> renderer) {
		registerSpecialTileEntityRenderer(supplier, dispatcher -> renderer);
	}
	
	public static <T extends BlockEntity> void registerSpecialTileEntityRenderer(Supplier<? extends BlockEntityType<T>> supplier, Function<? super BlockEntityRenderDispatcher, ? extends BlockEntityRenderer<? super T>> rendererFactory) {
		net.minecraftforge.fml.client.registry.ClientRegistry.bindTileEntityRenderer(supplier.get(), rendererFactory);
	}
	
	public static void registerKeybinding(KeyMapping key) {
		net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding(key);
	}
	
}
