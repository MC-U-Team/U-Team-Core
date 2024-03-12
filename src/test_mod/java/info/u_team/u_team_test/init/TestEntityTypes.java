package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.EntityTypeDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.entity.BetterEnderPearlEntity;
import info.u_team.u_team_test.entity.TestLivingEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;

public class TestEntityTypes {
	
	public static final EntityTypeDeferredRegister ENTITY_TYPES = EntityTypeDeferredRegister.create(TestMod.MODID);
	
	public static final RegistryObject<EntityType<BetterEnderPearlEntity>> BETTER_ENDERPEARL = ENTITY_TYPES.register("better_enderpearl", () -> EntityType.Builder.<BetterEnderPearlEntity> create(BetterEnderPearlEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setTrackingRange(128).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true));
	
	public static final RegistryObject<EntityType<TestLivingEntity>> TEST_LIVING = ENTITY_TYPES.register("test_living", () -> EntityType.Builder.<TestLivingEntity> create(TestLivingEntity::new, EntityClassification.MONSTER).size(1.05F, 3.4125F).trackingRange(8));
	
	public static void registerMod(IEventBus bus) {
		ENTITY_TYPES.register(bus);
	}
}
