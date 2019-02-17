package info.u_team.u_team_core.util.world;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.*;

public class WorldUtil {
	
	public static RayTraceResult rayTraceServerSide(EntityPlayer player, double range) {
		return rayTraceServerSide(player, range, RayTraceFluidMode.NEVER, false, true);
	}
	
	public static RayTraceResult rayTraceServerSide(EntityPlayer player, double range, RayTraceFluidMode liquidMode, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
		Vec3d playerVector = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		Vec3d lookVector = player.getLookVec();
		Vec3d locationVector = playerVector.add(lookVector.x * range, lookVector.y * range, lookVector.z * range);
		return player.world.rayTraceBlocks(playerVector, locationVector, liquidMode, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
	}
}
