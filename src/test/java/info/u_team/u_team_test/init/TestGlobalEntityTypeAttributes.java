package info.u_team.u_team_test.init;

import info.u_team.u_team_test.entity.TestLivingEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class TestGlobalEntityTypeAttributes {

	private static void entityAttributionCreation(EntityAttributeCreationEvent event) {
		event.put(TestEntityTypes.TEST_LIVING.get(), TestLivingEntity.createAttributes().build());
	}

	public static void registerMod(IEventBus bus) {
		bus.addListener(TestGlobalEntityTypeAttributes::entityAttributionCreation);
	}

}
