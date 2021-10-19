package info.u_team.u_team_test.init;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import info.u_team.u_team_test.screen.ButtonTestScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardKeyPressedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

public class TestKeys {
	
	public static final KeyMapping BASIC = new KeyMapping("Basic key", GLFW.GLFW_KEY_F8, "Basic category");
	
	private static void setup(FMLClientSetupEvent event) {
		ClientRegistry.registerKeyBinding(BASIC);
	}
	
	private static void onKeyboardPressed(KeyboardKeyPressedEvent.Post event) {
		if (BASIC.isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode()))) {
			Minecraft.getInstance().setScreen(new ButtonTestScreen());
			event.setCanceled(true);
		}
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(TestKeys::setup);
	}
	
	public static void registerForge(IEventBus bus) {
		bus.addListener(TestKeys::onKeyboardPressed);
	}
	
}
