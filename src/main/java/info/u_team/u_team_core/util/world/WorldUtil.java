package info.u_team.u_team_core.util.world;

import java.util.function.*;

import net.minecraft.entity.*;
import net.minecraft.entity.item.minecart.ContainerMinecartEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.*;
import net.minecraft.util.math.RayTraceContext.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.server.*;
import net.minecraft.world.storage.WorldSavedData;

/**
 * Some utility methods for world interaction.
 * 
 * @author HyCraftHD
 */
public class WorldUtil {
	
	/**
	 * Raytrace from an entities look vector for collisions in range. Use default block mode {@link BlockMode#OUTLINE} and
	 * fluid mode {@link FluidMode#NONE}.
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
		final Vector3d playerVector = entity.getPositionVec().add(0, entity.getEyeHeight(), 0);
		final Vector3d lookVector = entity.getLookVec();
		final Vector3d locationVector = playerVector.add(lookVector.x * range, lookVector.y * range, lookVector.z * range);
		return entity.world.rayTraceBlocks(new RayTraceContext(playerVector, locationVector, blockMode, fluidMode, entity));
	}
	
	/**
	 * Get a saved instance (own implementation) of {@link WorldSavedData}. If it does not exist, a new one is created.
	 * 
	 * @param <T> Custom world save data class
	 * @param world Server world
	 * @param name Name of this data
	 * @param defaultData Function for creating an instance and for the default instance
	 * @return An instance of <T> with the loaded data or default data.
	 */
	public static <T extends WorldSavedData> T getSaveData(ServerWorld world, String name, Function<String, T> defaultData) {
		return getSaveData(world, name, () -> defaultData.apply(name));
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
	
	/**
	 * Get the {@link ServerWorld} from the {@link DimensionType}
	 * 
	 * @param entity An entity used to get the server instance with {@link Entity#getServer()}
	 * @param type The dimension type
	 * @return The server world for the given type
	 */
	public static ServerWorld getServerWorld(Entity entity, RegistryKey<World> type) {
		return getServerWorld(entity.getServer(), type);
	}
	
	/**
	 * Get the {@link ServerWorld} from the {@link DimensionType}
	 * 
	 * @param server The server instance
	 * @param type The dimension type
	 * @return The server world for the given type
	 */
	public static ServerWorld getServerWorld(MinecraftServer server, RegistryKey<World> type) {
		return server.getWorld(type);
	}
	
	/**
	 * Teleports any entity to a given location in a given {@link ServerWorld}. Don't change the yaw and pitch of the
	 * entity.
	 * 
	 * @param entity The entity to teleport
	 * @param type The dimension type where the entity should be teleported. Can be the same as the current dimension type
	 *        or a different one
	 * @param pos The position the entity should be teleported to
	 */
	public static void teleportEntity(Entity entity, RegistryKey<World> type, BlockPos pos) {
		teleportEntity(entity, type, Vector3d.copyCentered(pos));
	}
	
	/**
	 * Teleports any entity to a given location in a given {@link ServerWorld}. Don't change the yaw and pitch of the
	 * entity.
	 * 
	 * @param entity The entity to teleport
	 * @param type The dimension type where the entity should be teleported. Can be the same as the current dimension type
	 *        or a different one
	 * @param pos The position the entity should be teleported to
	 */
	public static void teleportEntity(Entity entity, RegistryKey<World> type, Vector3d pos) {
		teleportEntity(entity, getServerWorld(entity, type), pos);
	}
	
	/**
	 * Teleports any entity to a given location in a given {@link ServerWorld}. Don't change the yaw and pitch of the
	 * entity.
	 * 
	 * @param entity The entity to teleport
	 * @param world The server world where the entity should be teleported. Can be the same as the current world or a
	 *        different one
	 * @param pos The position the entity should be teleported to
	 */
	public static void teleportEntity(Entity entity, ServerWorld world, BlockPos pos) {
		teleportEntity(entity, world, Vector3d.copyCentered(pos));
	}
	
	/**
	 * Teleports any entity to a given location in a given {@link ServerWorld}. Don't change the yaw and pitch of the
	 * entity.
	 * 
	 * @param entity The entity to teleport
	 * @param world The server world where the entity should be teleported. Can be the same as the current world or a
	 *        different one
	 * @param pos The position the entity should be teleported to
	 */
	public static void teleportEntity(Entity entity, ServerWorld world, Vector3d pos) {
		teleportEntity(entity, world, pos.getX(), pos.getY(), pos.getZ(), entity.rotationYaw, entity.rotationPitch);
	}
	
	/**
	 * Teleports any entity to a given location in a given {@link DimensionType}.
	 * 
	 * @param entity The entity to teleport
	 * @param type The dimension type where the entity should be teleported. Can be the same as the current dimension type
	 *        or a different one
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @param z Z-Coordinate
	 * @param yaw Yaw
	 * @param pitch Pitch
	 */
	public static void teleportEntity(Entity entity, RegistryKey<World> type, double x, double y, double z, float yaw, float pitch) {
		teleportEntity(entity, getServerWorld(entity, type), x, y, z, yaw, pitch);
	}
	
	/**
	 * Teleports any entity to a given location in a given {@link ServerWorld}.
	 * 
	 * @param entity The entity to teleport
	 * @param world The server world where the entity should be teleported. Can be the same as the current world or a
	 *        different one
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @param z Z-Coordinate
	 * @param yaw Yaw
	 * @param pitch Pitch
	 */
	public static void teleportEntity(Entity entity, ServerWorld world, double x, double y, double z, float yaw, float pitch) {
		teleportEntity(entity, world, x, y, z, yaw, pitch, true);
	}
	
	/**
	 * Teleports any entity to a given location in a given {@link ServerWorld}.
	 * 
	 * @param entity The entity to teleport
	 * @param world The server world where the entity should be teleported. Can be the same as the current world or a
	 *        different one
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @param z Z-Coordinate
	 * @param yaw Yaw
	 * @param pitch Pitch
	 * @param detach Detach the entity
	 */
	public static void teleportEntity(Entity entity, ServerWorld world, double x, double y, double z, float yaw, float pitch, boolean detach) {
		if (entity instanceof ServerPlayerEntity) {
			final ServerPlayerEntity player = (ServerPlayerEntity) entity;
			world.getChunkProvider().registerTicket(TicketType.POST_TELEPORT, new ChunkPos(new BlockPos(x, y, z)), 1, entity.getEntityId());
			if (detach) {
				player.stopRiding();
			}
			if (player.isSleeping()) {
				player.stopSleepInBed(true, true);
			}
			if (world == entity.world) {
				player.connection.setPlayerLocation(x, y, z, yaw, pitch);
			} else {
				player.teleport(world, x, y, z, yaw, pitch);
			}
			entity.setRotationYawHead(yaw);
		} else {
			final float wrapedYaw = MathHelper.wrapDegrees(yaw);
			final float wrapedPitch = MathHelper.clamp(MathHelper.wrapDegrees(pitch), -90.0F, 90.0F);
			if (world == entity.world) {
				entity.setLocationAndAngles(x, y, z, wrapedYaw, wrapedPitch);
				entity.setRotationYawHead(wrapedYaw);
			} else {
				if (detach) {
					entity.detach();
				}
				final Entity entityOld = entity;
				entity = entity.getType().create(world);
				if (entity == null) {
					return;
				}
				entity.copyDataFromOld(entityOld);
				if (entityOld instanceof ContainerMinecartEntity) {
					// Prevent duplication
					((ContainerMinecartEntity) entityOld).dropContentsWhenDead(false);
				}
				// Need to remove the old entity (Why the heck does TeleportCommand don't do
				// this and it works ?????)
				entityOld.remove(false);
				entity.setLocationAndAngles(x, y, z, wrapedYaw, wrapedPitch);
				entity.setRotationYawHead(wrapedYaw);
				world.addFromAnotherDimension(entity);
			}
		}
		
		if (!(entity instanceof LivingEntity) || !((LivingEntity) entity).isElytraFlying()) {
			entity.setMotion(entity.getMotion().mul(1, 0, 1));
			entity.setOnGround(true);
		}
		
		if (entity instanceof CreatureEntity) {
			((CreatureEntity) entity).getNavigator().clearPath();
		}
	}
}
