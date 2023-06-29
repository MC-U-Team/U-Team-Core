package info.u_team.u_team_core.impl;

import java.util.function.Function;

import info.u_team.u_team_core.util.DimensionTeleportUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraftforge.common.util.ITeleporter;

public class ForgeDimensionTeleportUtilHandler implements DimensionTeleportUtil.Handler {
	
	@Override
	public Entity changeDimension(Entity entity, ServerLevel destination, PortalInfo portalInfo) {
		return entity.changeDimension(destination, new ITeleporter() {
			
			@Override
			public PortalInfo getPortalInfo(Entity entity, ServerLevel destinationLevel, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
				return portalInfo;
			}
			
			@Override
			public Entity placeEntity(Entity entity, ServerLevel currentLevel, ServerLevel destinationLevel, float yaw, Function<Boolean, Entity> repositionEntity) {
				return repositionEntity.apply(false);
			}
		});
	}
	
}
