package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.EntityTypeRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.entity.BetterEnderPearl;
import info.u_team.u_team_test.entity.TestLiving;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class TestEntityTypes {
	
	public static final EntityTypeRegister ENTITY_TYPES = EntityTypeRegister.create(TestMod.MODID);
	
	public static final RegistryEntry<EntityType<BetterEnderPearl>> BETTER_ENDERPEARL = ENTITY_TYPES.register("better_enderpearl", () -> EntityType.Builder.<BetterEnderPearl> of(BetterEnderPearl::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(128).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true));
	
	public static final RegistryEntry<EntityType<TestLiving>> TEST_LIVING = ENTITY_TYPES.register("test_living", () -> EntityType.Builder.<TestLiving> of(TestLiving::new, MobCategory.MONSTER).sized(1.05F, 3.4125F).clientTrackingRange(8));
	
	public static void register() {
		ENTITY_TYPES.register();
	}
}
