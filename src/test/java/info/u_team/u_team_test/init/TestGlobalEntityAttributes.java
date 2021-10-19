package info.u_team.u_team_test.init;

import info.u_team.u_team_test.entity.TestLiving;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class TestGlobalEntityAttributes {
	
	private static void entityAttributionCreation(EntityAttributeCreationEvent event) {
		event.put(TestEntityTypes.TEST_LIVING.get(), TestLiving.createAttributes().build());
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(TestGlobalEntityAttributes::entityAttributionCreation);
	}
	
}
