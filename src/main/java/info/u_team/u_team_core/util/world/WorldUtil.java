package info.u_team.u_team_core.util.world;

import java.util.function.Function;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.WorldSavedData;

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
	
	public static <T extends WorldSavedData> T getSaveData(World world, String name, Function<String, T> function) {
		final DimensionType type = world.getDimension().getType();
		T instance = world.getSavedData(type, function, name);
		if (instance == null) {
			instance = function.apply(name);
			world.setSavedData(type, name, instance);
		}
		return instance;
	}
}
