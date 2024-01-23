package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.event.CommonEvents;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class TestMultiLoaderCommonEvents {
	
	private static void setup() {
		TestMultiLoaderReference.LOGGER.info("Hello from setup event");
	}
	
	private static void registryRegister(ResourceKey<? extends Registry<?>> key) {
		TestMultiLoaderReference.LOGGER.debug("Seen registry key {}", key);
	}
	
	static void register() {
		CommonEvents.registerSetup(TestMultiLoaderCommonEvents::setup);
		CommonEvents.registerRegister(TestMultiLoaderCommonEvents::registryRegister);
	}
	
}
