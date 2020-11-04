package info.u_team.u_team_test.init;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.*;

public class TestBiomeLoadingFeatures {
	
	private static void biomeLoadingAddition(BiomeLoadingEvent event) {
	}
	
	public static void registerForge(IEventBus bus) {
		bus.addListener(EventPriority.HIGH, TestBiomeLoadingFeatures::biomeLoadingAddition);
	}
	
}
