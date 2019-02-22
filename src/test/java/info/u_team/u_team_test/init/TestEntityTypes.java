package info.u_team.u_team_test.init;

import info.u_team.u_team_core.entitytype.UEntityType;
import info.u_team.u_team_core.registry.EntityTypeRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.entity.EntityBetterEnderPearl;
import net.minecraft.entity.EntityType;

public class TestEntityTypes {
	
	public static final EntityType<EntityBetterEnderPearl> better_enderpearl = UEntityType.Builder.create("better_enderpearl", EntityBetterEnderPearl.class, EntityBetterEnderPearl::new).tracker(128, 20, true).build();
	
	public static void construct() {
		EntityTypeRegistry.register(TestMod.modid, TestEntityTypes.class);
	}
	
}
