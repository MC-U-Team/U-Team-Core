package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.SpawnPlacementRegister;
import info.u_team.u_team_core.event.SetupEvents;
import info.u_team.u_team_core.impl.common.CommonSpawnPlacementRegister;
import info.u_team.u_team_core.util.CastUtil;
import net.minecraft.world.entity.SpawnPlacements;

public class FabricSpawnPlacementRegister extends CommonSpawnPlacementRegister {
	
	FabricSpawnPlacementRegister() {
	}
	
	@Override
	public void register() {
		SetupEvents.COMMON_SETUP.register(this::setup);
	}
	
	private void setup() {
		spawnPlacements.forEach((supplier, holder) -> {
			SpawnPlacements.register(CastUtil.uncheckedCast(supplier.get()), holder.placementType(), holder.heightmap(), holder.predicate());
		});
	}
	
	public static class Factory implements SpawnPlacementRegister.Factory {
		
		@Override
		public SpawnPlacementRegister create() {
			return new FabricSpawnPlacementRegister();
		}
	}
	
}
