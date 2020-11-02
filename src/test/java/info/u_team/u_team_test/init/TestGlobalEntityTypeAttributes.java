package info.u_team.u_team_test.init;

import info.u_team.u_team_test.entity.TestLivingEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class TestGlobalEntityTypeAttributes {
	
	private static void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			GlobalEntityTypeAttributes.put(TestEntityTypes.TEST_LIVING.get(), TestLivingEntity.registerAttributes().create());
		});
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(TestGlobalEntityTypeAttributes::setup);
	}
	
}
