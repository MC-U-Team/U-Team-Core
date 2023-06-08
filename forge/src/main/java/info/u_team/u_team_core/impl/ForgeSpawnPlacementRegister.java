package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.SpawnPlacementRegister;
import info.u_team.u_team_core.impl.common.CommonSpawnPlacementRegister;
import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;

public class ForgeSpawnPlacementRegister extends CommonSpawnPlacementRegister {
	
	ForgeSpawnPlacementRegister() {
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(bus -> bus.addListener(this::spawnPlacementRegister));
	}
	
	private void spawnPlacementRegister(SpawnPlacementRegisterEvent event) {
		spawnPlacements.forEach((supplier, holder) -> {
			event.register(CastUtil.uncheckedCast(supplier.get()), holder.placementType(), holder.heightmap(), holder.predicate(), SpawnPlacementRegisterEvent.Operation.OR);
		});
	}
	
	public static class Factory implements SpawnPlacementRegister.Factory {
		
		@Override
		public SpawnPlacementRegister create() {
			return new ForgeSpawnPlacementRegister();
		}
	}
	
}
