package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.util.DimensionTeleportUtil;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;

public class FabricDimensionTeleportUtilHandler implements DimensionTeleportUtil.Handler {
	
	@Override
	public Entity changeDimension(Entity entity, ServerLevel destination, PortalInfo portalInfo) {
		return FabricDimensions.teleport(entity, destination, portalInfo);
	}
	
}
