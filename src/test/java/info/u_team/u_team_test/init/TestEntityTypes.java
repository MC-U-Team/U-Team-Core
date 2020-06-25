package info.u_team.u_team_test.init;

import info.u_team.u_team_core.entitytype.UEntityType;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.entity.BetterEnderPearlEntity;
import net.minecraft.entity.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

public class TestEntityTypes {
	
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, TestMod.MODID);
	
	public static final EntityType<BetterEnderPearlEntity> BETTER_ENDERPEARL = UEntityType.UBuilder.<BetterEnderPearlEntity> create("better_enderpearl", BetterEnderPearlEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setTrackingRange(128).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true).build();
	
	
	public static void register(IEventBus bus) {
		ENTITY_TYPES.register(bus);
	}
}
