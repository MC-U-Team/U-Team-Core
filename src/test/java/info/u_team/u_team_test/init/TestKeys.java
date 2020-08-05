package info.u_team.u_team_test.init;

import org.lwjgl.glfw.GLFW;

import info.u_team.u_team_core.util.registry.ClientRegistry;
import info.u_team.u_team_test.screen.ButtonTestScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardKeyPressedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class TestKeys {
	
	public static final KeyBinding BASIC = new KeyBinding("Basic key", GLFW.GLFW_KEY_F8, "Basic category");
	
	private static void setup(FMLClientSetupEvent event) {
		ClientRegistry.registerKeybinding(BASIC);
	}
	
	private static void onKeyboardPressed(KeyboardKeyPressedEvent.Post event) {
		if (BASIC.isActiveAndMatches(InputMappings.getInputByCode(event.getKeyCode(), event.getScanCode()))) {
			Minecraft.getInstance().displayGuiScreen(new ButtonTestScreen());
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
