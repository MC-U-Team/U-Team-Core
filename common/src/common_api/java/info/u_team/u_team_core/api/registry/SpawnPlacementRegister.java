package info.u_team.u_team_core.api.registry;

import java.util.function.Supplier;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public interface SpawnPlacementRegister {
	
	static SpawnPlacementRegister create() {
		return Factory.INSTANCE.create();
	}
	
	<T extends Mob> void register(Supplier<? extends EntityType<T>> supplier, SpawnPlacementType placementType, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate);
	
	void register();
	
	interface Factory {
		
		Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
		
		SpawnPlacementRegister create();
	}
	
}
