package info.u_team.u_team_test.test_multiloader.init;

import com.mojang.blaze3d.platform.InputConstants;

import info.u_team.u_team_core.api.event.ClientEvents;
import info.u_team.u_team_core.util.KeyMappingUtil;
import info.u_team.u_team_test.test_multiloader.messages.TestClientToServerMessage;
import info.u_team.u_team_test.test_multiloader.screen.ButtonTestScreen;
import info.u_team.u_team_test.test_multiloader.screen.ButtonTestScreenVanilla;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class TestMultiLoaderClientEvents {
	
	private static void onEndClientTick(Minecraft minecraft) {
		while (TestMultiLoaderKeyMappings.TEST_NETWORK_EXPLICIT_C2S.get().consumeClick()) {
			TestMultiLoaderNetwork.NETWORK.sendToServer(new TestClientToServerMessage("Hello server"));
		}
	}
	
	private static boolean onScreenAfterKeyPressed(Screen screen, int keyCode, int scanCode, int modifiers) {
		if (KeyMappingUtil.matches(TestMultiLoaderKeyMappings.TEST_GUI_SCREEN.get(), InputConstants.getKey(keyCode, scanCode))) {
			Minecraft.getInstance().setScreen(new ButtonTestScreen());
			return true;
		}
		if (KeyMappingUtil.matches(TestMultiLoaderKeyMappings.TEST_VANILLA_GUI_SCREEN.get(), InputConstants.getKey(keyCode, scanCode))) {
			Minecraft.getInstance().setScreen(new ButtonTestScreenVanilla());
			return true;
		}
		return false;
	}
	
	static void register() {
		ClientEvents.registerEndClientTick(TestMultiLoaderClientEvents::onEndClientTick);
		ClientEvents.registerScreenAfterKeyPressed(TestMultiLoaderClientEvents::onScreenAfterKeyPressed);
	}
	
}
