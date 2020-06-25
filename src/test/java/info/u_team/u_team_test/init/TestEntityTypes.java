package info.u_team.u_team_test.init;

import info.u_team.u_team_core.entitytype.UEntityType;
import info.u_team.u_team_test.entity.BetterEnderPearlEntity;
import net.minecraft.entity.*;

public class TestEntityTypes {
	
	public static final EntityType<BetterEnderPearlEntity> BETTER_ENDERPEARL = UEntityType.UBuilder.<BetterEnderPearlEntity> create("better_enderpearl", BetterEnderPearlEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setTrackingRange(128).setUpdateInterval(20).setShouldReceiveVelocityUpdates(true).build();
	
}
