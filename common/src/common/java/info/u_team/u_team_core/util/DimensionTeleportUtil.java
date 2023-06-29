package info.u_team.u_team_core.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;

public class DimensionTeleportUtil {
	
	private static final Handler HANDLER = ServiceUtil.loadOne(Handler.class);
	
	public static Entity changeDimension(Entity entity, ServerLevel destination, PortalInfo portalInfo) {
		return HANDLER.changeDimension(entity, destination, portalInfo);
	}
	
	public static interface Handler {
		
		Entity changeDimension(Entity entity, ServerLevel destination, PortalInfo portalInfo);
	}
	
}
