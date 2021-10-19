package info.u_team.u_team_core.util;

import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Some utility methods for world interaction.
 *
 * @author HyCraftHD
 */
public class LevelUtil {
	
	/**
	 * Raytrace from an entities look vector for collisions in range. Use default block mode {@link BlockMode#OUTLINE} and
	 * fluid mode {@link FluidMode#NONE}.
	 *
	 * @param entity Entity from where we get the look vector
	 * @param range Range in blocks
	 * @return Raytrace result with information about the trace
	 */
	public static HitResult rayTraceServerSide(Entity entity, double range) {
		return rayTraceServerSide(entity, range, Block.OUTLINE, Fluid.NONE);
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
	public static HitResult rayTraceServerSide(Entity entity, double range, Block blockMode, Fluid fluidMode) {
		final var playerVector = entity.position().add(0, entity.getEyeHeight(), 0);
		final var lookVector = entity.getLookAngle();
		final var locationVector = playerVector.add(lookVector.x * range, lookVector.y * range, lookVector.z * range);
		return entity.level.clip(new ClipContext(playerVector, locationVector, blockMode, fluidMode, entity));
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
	public static <T extends SavedData> T getSaveData(ServerLevel world, Function<CompoundTag, T> load, String name, Function<String, T> defaultData) {
		return getSaveData(world, name, load, () -> defaultData.apply(name));
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
	public static <T extends SavedData> T getSaveData(ServerLevel world, String name, Function<CompoundTag, T> load, Supplier<T> defaultData) {
		return world.getDataStorage().computeIfAbsent(load, defaultData, name);
	}
	
	/**
	 * Get the {@link ServerLevel} from the {@link ResourceKey}
	 *
	 * @param entity An entity used to get the server instance with {@link Entity#getServer()}
	 * @param type The dimension type
	 * @return The server world for the given type
	 */
	public static ServerLevel getServerLevel(Entity entity, ResourceKey<Level> type) {
		return getServerWorld(entity.getServer(), type);
	}
	
	/**
	 * Get the {@link ServerLevel} from the {@link ResourceKey}
	 *
	 * @param server The server instance
	 * @param type The dimension type
	 * @return The server world for the given type
	 */
	public static ServerLevel getServerWorld(MinecraftServer server, ResourceKey<Level> type) {
		return server.getLevel(type);
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
	public static void teleportEntity(Entity entity, ResourceKey<Level> type, BlockPos pos) {
		teleportEntity(entity, type, Vec3.atCenterOf(pos));
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
	public static void teleportEntity(Entity entity, ResourceKey<Level> type, Vec3 pos) {
		teleportEntity(entity, getServerLevel(entity, type), pos);
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
	public static void teleportEntity(Entity entity, ServerLevel world, BlockPos pos) {
		teleportEntity(entity, world, Vec3.atCenterOf(pos));
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
	public static void teleportEntity(Entity entity, ServerLevel world, Vec3 pos) {
		teleportEntity(entity, world, pos.x(), pos.y(), pos.z(), entity.getYRot(), entity.getXRot());
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
	public static void teleportEntity(Entity entity, ResourceKey<Level> type, double x, double y, double z, float yaw, float pitch) {
		teleportEntity(entity, getServerLevel(entity, type), x, y, z, yaw, pitch);
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
	public static void teleportEntity(Entity entity, ServerLevel world, double x, double y, double z, float yaw, float pitch) {
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
	public static void teleportEntity(Entity entity, ServerLevel world, double x, double y, double z, float yaw, float pitch, boolean detach) {
		if (entity instanceof ServerPlayer) {
			final var player = (ServerPlayer) entity;
			world.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, new ChunkPos(new BlockPos(x, y, z)), 1, entity.getId());
			if (detach) {
				player.stopRiding();
			}
			if (player.isSleeping()) {
				player.stopSleepInBed(true, true);
			}
			if (world == entity.level) {
				player.connection.teleport(x, y, z, yaw, pitch);
			} else {
				player.teleportTo(world, x, y, z, yaw, pitch);
			}
			entity.setYHeadRot(yaw);
		} else {
			final var wrapedYaw = Mth.wrapDegrees(yaw);
			final var wrapedPitch = Mth.clamp(Mth.wrapDegrees(pitch), -90.0F, 90.0F);
			if (world == entity.level) {
				entity.moveTo(x, y, z, wrapedYaw, wrapedPitch);
				entity.setYHeadRot(wrapedYaw);
			} else {
				if (detach) {
					entity.unRide();
				}
				final var entityOld = entity;
				entity = entity.getType().create(world);
				if (entity == null) {
					return;
				}
				entity.restoreFrom(entityOld);
				// if (entityOld instanceof AbstractMinecartContainer) { // TODO is this still needed?
				// // Prevent duplication
				// ((AbstractMinecartContainer) entityOld).dropContentsWhenDead(false);
				// }
				entity.moveTo(x, y, z, wrapedYaw, wrapedPitch);
				entity.setYHeadRot(wrapedYaw);
				entityOld.setRemoved(Entity.RemovalReason.CHANGED_DIMENSION);
				world.addDuringTeleport(entity);
			}
		}
		
		if (!(entity instanceof LivingEntity) || !((LivingEntity) entity).isFallFlying()) {
			entity.setDeltaMovement(entity.getDeltaMovement().multiply(1, 0, 1));
			entity.setOnGround(true);
		}
		
		if (entity instanceof PathfinderMob) {
			((PathfinderMob) entity).getNavigation().stop();
		}
	}
}
