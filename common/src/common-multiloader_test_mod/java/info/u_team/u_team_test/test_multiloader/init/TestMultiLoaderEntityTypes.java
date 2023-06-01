package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.EntityTypeRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import info.u_team.u_team_test.test_multiloader.entity.TestEnderPearl;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class TestMultiLoaderEntityTypes {
	
	public static final EntityTypeRegister ENTITY_TYPES = EntityTypeRegister.create(TestMultiLoaderReference.MODID);
	
	public static final RegistryEntry<EntityType<TestEnderPearl>> TEST_ENDERPEARL = ENTITY_TYPES.register("test_enderpearl", () -> EntityType.Builder.<TestEnderPearl> of(TestEnderPearl::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(8).updateInterval(10));
	
	static void register() {
		ENTITY_TYPES.register();
	}
	
}
