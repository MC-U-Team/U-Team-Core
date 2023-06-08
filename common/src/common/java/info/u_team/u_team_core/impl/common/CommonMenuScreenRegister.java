package info.u_team.u_team_core.impl.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.client.MenuScreenRegister;
import net.minecraft.client.gui.screens.MenuScreens.ScreenConstructor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public abstract class CommonMenuScreenRegister implements MenuScreenRegister {
	
	protected final Map<Supplier<? extends MenuType<?>>, ScreenConstructor<?, ?>> screens;
	
	protected CommonMenuScreenRegister() {
		screens = new HashMap<>();
	}
	
	@Override
	public <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void register(Supplier<? extends MenuType<? extends M>> supplier, ScreenConstructor<M, U> constructor) {
		screens.put(supplier, constructor);
	}
	
}
