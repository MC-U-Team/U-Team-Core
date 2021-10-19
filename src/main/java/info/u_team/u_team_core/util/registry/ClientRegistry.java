package info.u_team.u_team_core.util.registry;

import java.util.function.Supplier;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

@Deprecated(forRemoval = true) // TODO remove when everything as replacement
public class ClientRegistry {
	
	@Deprecated // TODO remove
	public static <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void registerScreen(Supplier<? extends MenuType<? extends M>> supplier, MenuScreens.ScreenConstructor<M, U> factory) {
		MenuScreens.register(supplier.get(), factory);
	}
	
	@Deprecated // TODO add event too. Currently no replacement
	public static void registerKeybinding(KeyMapping key) {
		net.minecraftforge.fmlclient.registry.ClientRegistry.registerKeyBinding(key);
	}
	
}
