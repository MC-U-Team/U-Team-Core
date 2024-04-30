package info.u_team.u_team_core.impl.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.SpawnPlacementRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public abstract class CommonSpawnPlacementRegister implements SpawnPlacementRegister {
	
	protected final Map<Supplier<? extends EntityType<? extends Mob>>, Holder<?>> spawnPlacements;
	
	protected CommonSpawnPlacementRegister() {
		spawnPlacements = new HashMap<>();
	}
	
	@Override
	public <T extends Mob> void register(Supplier<? extends EntityType<T>> supplier, SpawnPlacementType placementType, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate) {
		spawnPlacements.put(supplier, new Holder<>(placementType, heightmap, predicate));
	}
	
	protected record Holder<T extends Mob>(SpawnPlacementType placementType, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate) {
	}
	
}
