package info.u_team.u_team_test.init;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import info.u_team.u_team_test.screen.ButtonTestScreen;
import info.u_team.u_team_test.screen.ButtonTestScreenVanilla;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent.KeyPressed;
import net.minecraftforge.eventbus.api.IEventBus;

public class TestKeys {
	
	public static final KeyMapping BASIC = new KeyMapping("Basic key", GLFW.GLFW_KEY_F8, "Basic category");
	public static final KeyMapping BASIC_VANILLA = new KeyMapping("Basic key for Vanilla test", GLFW.GLFW_KEY_F9, "Basic category");
	
	private static void register(RegisterKeyMappingsEvent event) {
		event.register(BASIC);
		event.register(BASIC_VANILLA);
	}
	
	private static void onKeyboardPressed(KeyPressed.Post event) {
		if (BASIC.isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode()))) {
			Minecraft.getInstance().setScreen(new ButtonTestScreen());
			event.setCanceled(true);
		}
		
		if (BASIC_VANILLA.isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode()))) {
			Minecraft.getInstance().setScreen(new ButtonTestScreenVanilla());
			event.setCanceled(true);
		}
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(TestKeys::register);
	}
	
	public static void registerForge(IEventBus bus) {
		bus.addListener(TestKeys::onKeyboardPressed);
	}
	
}
