package info.u_team.u_team_core.util.world;

import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.*;
import net.minecraft.util.math.RayTraceContext.*;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

public class WorldUtil {
	
	public static RayTraceResult rayTraceServerSide(PlayerEntity player, double range) {
		return rayTraceServerSide(player, range, BlockMode.OUTLINE, FluidMode.NONE);
	}
	
	public static RayTraceResult rayTraceServerSide(PlayerEntity player, double range, BlockMode blockMode, FluidMode fluidMode) {
		final Vec3d playerVector = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		final Vec3d lookVector = player.getLookVec();
		final Vec3d locationVector = playerVector.add(lookVector.x * range, lookVector.y * range, lookVector.z * range);
		return player.world.rayTraceBlocks(new RayTraceContext(playerVector, locationVector, blockMode, fluidMode, player));
	}
	
	public static <T extends WorldSavedData> T getSaveData(ServerWorld world, String name, Supplier<T> function) {
		return world.getSavedData().getOrCreate(function, name);
	}
}
