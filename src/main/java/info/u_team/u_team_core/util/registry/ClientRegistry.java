package info.u_team.u_team_core.util.registry;

import java.util.function.Supplier;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmlclient.registry.RenderingRegistry;

public class ClientRegistry {

	public static <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void registerScreen(Supplier<? extends MenuType<? extends M>> supplier, MenuScreens.ScreenConstructor<M, U> factory) {
		MenuScreens.register(supplier.get(), factory);
	}

	public static <T extends Entity> void registerEntityRenderer(Supplier<? extends EntityType<? extends T>> supplier, EntityRendererProvider<T> provider) {
		RenderingRegistry.registerEntityRenderingHandler(supplier.get(), provider);
	}

	public static <T extends BlockEntity> void registerSpecialTileEntityRenderer(Supplier<? extends BlockEntityType<T>> supplier, BlockEntityRendererProvider<T> provider) {
		BlockEntityRenderers.register(supplier.get(), provider);
	}

	public static void registerKeybinding(KeyMapping key) {
		net.minecraftforge.fmlclient.registry.ClientRegistry.registerKeyBinding(key);
	}

}
