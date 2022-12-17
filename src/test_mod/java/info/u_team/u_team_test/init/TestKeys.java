package info.u_team.u_team_test.init;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import info.u_team.u_team_test.screen.ButtonTestScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent.KeyPressed;
import net.minecraftforge.eventbus.api.IEventBus;

public class TestKeys {
	
	public static final KeyMapping BASIC = new KeyMapping("Basic key", GLFW.GLFW_KEY_F8, "Basic category");
	
	private static void register(RegisterKeyMappingsEvent event) {
		event.register(BASIC);
	}
	
	private static void onKeyboardPressed(KeyPressed.Post event) {
		if (BASIC.isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode()))) {
			Minecraft.getInstance().setScreen(new ButtonTestScreen());
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