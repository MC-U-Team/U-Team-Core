package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.EntityTypeDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.entity.BetterEnderPearlEntity;
import info.u_team.u_team_test.entity.TestLivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;

public class TestEntityTypes {
	
	public static final EntityTypeDeferredRegister ENTITY_TYPES = EntityTypeDeferredRegister.create(TestMod.MODID);
	
	public static final RegistryObject<EntityType<BetterEnderPearlEntity>> BETTER_ENDERPEARL = ENTITY_TYPES.register("better_enderpearl", () -> EntityType.Builder.<BetterEnderPearlEntity> of(BetterEnderPearlEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(128).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true));
	
	public static final RegistryObject<EntityType<TestLivingEntity>> TEST_LIVING = ENTITY_TYPES.register("test_living", () -> EntityType.Builder.<TestLivingEntity> of(TestLivingEntity::new, MobCategory.MONSTER).sized(1.05F, 3.4125F).clientTrackingRange(8));
	
	public static void registerMod(IEventBus bus) {
		ENTITY_TYPES.register(bus);
	}
}
