package info.u_team.u_team_core.event;

import java.util.function.Supplier;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.MenuScreens.ScreenConstructor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;

public class RegisterMenuScreensEvent extends Event implements IModBusEvent {
	
	/**
	 * Registers a menu screen.
	 *
	 * @param menuTypeSupplier The menu type as a supplier to register a screen for
	 * @param screenConstructor The constructor of the screen
	 */
	public <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void registerScreen(Supplier<? extends MenuType<? extends M>> menuTypeSupplier, ScreenConstructor<M, U> screenConstructor) {
		registerScreen(menuTypeSupplier.get(), screenConstructor);
	}
	
	/**
	 * Registers a menu screen.
	 *
	 * @param menuType The menu type to register a screen for
	 * @param screenConstructor The constructor of the screen
	 */
	public <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void registerScreen(MenuType<? extends M> menuType, ScreenConstructor<M, U> screenConstructor) {
		MenuScreens.register(menuType, screenConstructor);
	}
	
}
