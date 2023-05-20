package info.u_team.u_team_test.test_multiloader.init;

import org.lwjgl.glfw.GLFW;

import info.u_team.u_team_core.api.registry.LazyEntry;
import info.u_team.u_team_core.api.registry.client.KeyMappingRegister;
import net.minecraft.client.KeyMapping;

public class TestMultiLoaderKeyMappings {
	
	public static final KeyMappingRegister KEY_MAPPINGS = KeyMappingRegister.create();
	
	public static final LazyEntry<KeyMapping> TEST_NETWORK_EXPLICIT_C2S = KEY_MAPPINGS.register(() -> new KeyMapping("Explicit C2S", GLFW.GLFW_KEY_KP_1, "Test Network"));
	
	static void register() {
		KEY_MAPPINGS.register();
	}
	
}
