package info.u_team.u_team_test.init;

import org.lwjgl.glfw.GLFW;

import info.u_team.u_team_core.util.registry.ClientRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.gui.ButtonTestScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardKeyPressedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = TestMod.MODID, value = Dist.CLIENT)
public class TestKeys {
	
	public static final KeyBinding BASIC = new KeyBinding("Basic key", GLFW.GLFW_KEY_F8, "Basic category");
	
	public static void construct() {
		ClientRegistry.registerKeybinding(BASIC);
	}
	
	@SubscribeEvent
	public static void keyPressInGui(KeyboardKeyPressedEvent.Post event) {
		if (BASIC.isActiveAndMatches(InputMappings.getInputByCode(event.getKeyCode(), event.getScanCode()))) {
			Minecraft.getInstance().displayGuiScreen(new ButtonTestScreen());
			event.setCanceled(true);
		}
	}
	
}
