package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.EntityTypeDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.entity.BetterEnderPearl;
import info.u_team.u_team_test.entity.TestLiving;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;

public class TestEntityTypes {
	
	public static final EntityTypeDeferredRegister ENTITY_TYPES = EntityTypeDeferredRegister.create(TestMod.MODID);
	
	public static final RegistryObject<EntityType<BetterEnderPearl>> BETTER_ENDERPEARL = ENTITY_TYPES.register("better_enderpearl", () -> EntityType.Builder.<BetterEnderPearl> of(BetterEnderPearl::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(128).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true));
	
	public static final RegistryObject<EntityType<TestLiving>> TEST_LIVING = ENTITY_TYPES.register("test_living", () -> EntityType.Builder.<TestLiving> of(TestLiving::new, MobCategory.MONSTER).sized(1.05F, 3.4125F).clientTrackingRange(8));
	
	public static void registerMod(IEventBus bus) {
		ENTITY_TYPES.register(bus);
	}
}
