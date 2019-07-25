package info.u_team.u_team_core.util.world;

import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.*;
import net.minecraft.util.math.RayTraceContext.*;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

/**
 * Some utility methods for world interaction.
 * 
 * @author HyCraftHD
 *
 */
public class WorldUtil {
	
	/**
	 * Raytrace from an entities look vector for collisions in range. Use default block mode {@link BlockMode#OUTLINE} and
	 * fluid mode {@link FluidMode#NONE}.
	 * 
	 * @see WorldUtil#rayTraceServerSide(Entity, double, BlockMode, FluidMode)
	 * 
	 * @param entity Entity from where we get the look vector
	 * @param range Range in blocks
	 * @return Raytrace result with information about the trace
	 */
	public static RayTraceResult rayTraceServerSide(Entity entity, double range) {
		return rayTraceServerSide(entity, range, BlockMode.OUTLINE, FluidMode.NONE);
	}
	
	/**
	 * Raytrace from an entities look vector for collisions in range.
	 * 
	 * @param entity Entity from where we get the look vector
	 * @param range Range in blocks
	 * @param blockMode Mode for block collisions
	 * @param fluidMode Mode for fluid collisions
	 * @return Raytrace result with information about the trace
	 */
	public static RayTraceResult rayTraceServerSide(Entity entity, double range, BlockMode blockMode, FluidMode fluidMode) {
		final Vec3d playerVector = new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
		final Vec3d lookVector = entity.getLookVec();
		final Vec3d locationVector = playerVector.add(lookVector.x * range, lookVector.y * range, lookVector.z * range);
		return entity.world.rayTraceBlocks(new RayTraceContext(playerVector, locationVector, blockMode, fluidMode, entity));
	}
	
	/**
	 * Get a saved instance (own implementation) of {@link WorldSavedData}. If it does not exist, a new one is created.
	 * 
	 * @param <T> Custom world save data class
	 * @param world Server world
	 * @param name Name of this data
	 * @param defaultData Supplier for creating an instance and for the default instance
	 * @return An instance of <T> with the loaded data or default data.
	 */
	public static <T extends WorldSavedData> T getSaveData(ServerWorld world, String name, Supplier<T> defaultData) {
		return world.getSavedData().getOrCreate(defaultData, name);
	}
}
