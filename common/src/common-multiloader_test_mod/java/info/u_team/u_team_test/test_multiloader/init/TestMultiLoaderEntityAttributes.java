package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.EntityAttributeRegister;
import info.u_team.u_team_test.test_multiloader.entity.TestLiving;
import net.minecraft.Util;

public class TestMultiLoaderEntityAttributes {
	
	public static final EntityAttributeRegister ENTITY_ATTRIBUTES = Util.make(EntityAttributeRegister.create(), entityAttributes -> {
		entityAttributes.register(TestMultiLoaderEntityTypes.TEST_LIVING, TestLiving.createAttributes());
	});
	
	static void register() {
		ENTITY_ATTRIBUTES.register();
	}
	
}
