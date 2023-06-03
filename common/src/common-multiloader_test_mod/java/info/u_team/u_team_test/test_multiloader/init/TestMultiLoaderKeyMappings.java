package info.u_team.u_team_test.test_multiloader.init;

import org.lwjgl.glfw.GLFW;

import info.u_team.u_team_core.api.registry.LazyEntry;
import info.u_team.u_team_core.api.registry.client.KeyMappingRegister;
import net.minecraft.client.KeyMapping;

public class TestMultiLoaderKeyMappings {
	
	public static final KeyMappingRegister KEY_MAPPINGS = KeyMappingRegister.create();
	
	public static final LazyEntry<KeyMapping> TEST_NETWORK_EXPLICIT_C2S = KEY_MAPPINGS.register(() -> new KeyMapping("Explicit C2S", GLFW.GLFW_KEY_KP_1, "Test Network"));
	
	public static final LazyEntry<KeyMapping> TEST_GUI_SCREEN = KEY_MAPPINGS.register(() -> new KeyMapping("Open gui test screen", GLFW.GLFW_KEY_F8, "Test Gui"));
	public static final LazyEntry<KeyMapping> TEST_VANILLA_GUI_SCREEN = KEY_MAPPINGS.register(() -> new KeyMapping("Open vanilla gui test screen", GLFW.GLFW_KEY_F9, "Test Gui"));
	
	static void register() {
		KEY_MAPPINGS.register();
	}
	
}
