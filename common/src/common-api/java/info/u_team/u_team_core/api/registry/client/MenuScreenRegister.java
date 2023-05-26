package info.u_team.u_team_core.api.registry.client;

import java.util.function.Supplier;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.client.gui.screens.MenuScreens.ScreenConstructor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public interface MenuScreenRegister {
	
	static MenuScreenRegister create() {
		return Factory.INSTANCE.create();
	}
	
	<M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void register(Supplier<? extends MenuType<? extends M>> supplier, ScreenConstructor<M, U> constructor);
	
	void register();
	
	interface Factory {
		
		Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
		
		MenuScreenRegister create();
	}
	
}
