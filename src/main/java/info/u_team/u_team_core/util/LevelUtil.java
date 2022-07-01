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
 * Some utility methods for level interaction.
 *
 * @author HyCraftHD
 */
public class LevelUtil {
	
	/**
	 * Raytrace from an entities look vector for collisions in range. Use default block mode {@link Block#OUTLINE} and fluid
	 * mode {@link Fluid#NONE}.
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
		final Vec3 playerVector = entity.position().add(0, entity.getEyeHeight(), 0);
		final Vec3 lookVector = entity.getLookAngle();
		final Vec3 locationVector = playerVector.add(lookVector.x * range, lookVector.y * range, lookVector.z * range);
		return entity.level.clip(new ClipContext(playerVector, locationVector, blockMode, fluidMode, entity));
	}
	
	/**
	 * Get a saved instance (own implementation) of {@link SavedData}. If it does not exist, a new one is created.
	 *
	 * @param <T> Custom level save data class
	 * @param level Server level
	 * @param name Name of this data
	 * @param defaultData Function for creating an instance and for the default instance
	 * @return An instance of <T> with the loaded data or default data.
	 */
	public static <T extends SavedData> T getSaveData(ServerLevel level, Function<CompoundTag, T> load, String name, Function<String, T> defaultData) {
		return getSaveData(level, name, load, () -> defaultData.apply(name));
	}
	
	/**
	 * Get a saved instance (own implementation) of {@link SavedData}. If it does not exist, a new one is created.
	 *
	 * @param <T> Custom level save data class
	 * @param level Server level
	 * @param name Name of this data
	 * @param defaultData Supplier for creating an instance and for the default instance
	 * @return An instance of <T> with the loaded data or default data.
	 */
	public static <T extends SavedData> T getSaveData(ServerLevel level, String name, Function<CompoundTag, T> load, Supplier<T> defaultData) {
		return level.getDataStorage().computeIfAbsent(load, defaultData, name);
	}
	
	/**
	 * Get the {@link ServerLevel} from the {@link ResourceKey}
	 *
	 * @param entity An entity used to get the server instance with {@link Entity#getServer()}
	 * @param key The dimension key
	 * @return The server level for the given key
	 */
	public static ServerLevel getServerLevel(Entity entity, ResourceKey<Level> key) {
		return getServerLevel(entity.getServer(), key);
	}
	
	/**
	 * Get the {@link ServerLevel} from the {@link ResourceKey}
	 *
	 * @param server The server instance
	 * @param key The dimension key
	 * @return The server level for the given key
	 */
	public static ServerLevel getServerLevel(MinecraftServer server, ResourceKey<Level> key) {
		return server.getLevel(key);
	}
	
	/**
	 * Teleports any entity to a given location in a given {@link ServerLevel}. Don't change the yaw and pitch of the
	 * entity.
	 *
	 * @param entity The entity to teleport
	 * @param key The dimension key where the entity should be teleported. Can be the same as the current dimension key or a
	 *        different one
	 * @param pos The position the entity should be teleported to
	 * @return The teleported entity
	 */
	public static Entity teleportEntity(Entity entity, ResourceKey<Level> key, BlockPos pos) {
		return teleportEntity(entity, key, Vec3.atCenterOf(pos));
	}
	
	/**
	 * Teleports any entity to a given location in a given {@link ServerLevel}. Don't change the yaw and pitch of the
	 * entity.
	 *
	 * @param entity The entity to teleport
	 * @param key The dimension key where the entity should be teleported. Can be the same as the current dimension key or a
	 *        different one
	 * @param pos The position the entity should be teleported to
	 * @return The teleported entity
	 */
	public static Entity teleportEntity(Entity entity, ResourceKey<Level> key, Vec3 pos) {
		return teleportEntity(entity, getServerLevel(entity, key), pos);
	}
	
	/**
	 * Teleports any entity to a given location in a given {@link ServerLevel}. Don't change the yaw and pitch of the
	 * entity.
	 *
	 * @param entity The entity to teleport
	 * @param level The server level where the entity should be teleported. Can be the same as the current level or a
	 *        different one
	 * @param pos The position the entity should be teleported to
	 * @return The teleported entity
	 */
	public static Entity teleportEntity(Entity entity, ServerLevel level, BlockPos pos) {
		return teleportEntity(entity, level, Vec3.atCenterOf(pos));
	}
	
	/**
	 * Teleports any entity to a given location in a given {@link ServerLevel}. Don't change the yaw and pitch of the
	 * entity.
	 *
	 * @param entity The entity to teleport
	 * @param level The server level where the entity should be teleported. Can be the same as the current level or a
	 *        different one
	 * @param pos The position the entity should be teleported to
	 * @return The teleported entity
	 */
	public static Entity teleportEntity(Entity entity, ServerLevel level, Vec3 pos) {
		return teleportEntity(entity, level, pos.x(), pos.y(), pos.z(), entity.getYRot(), entity.getXRot());
	}
	
	/**
	 * Teleports any entity to a given location in a given {@link DimensionType}.
	 *
	 * @param entity The entity to teleport
	 * @param key The dimension key where the entity should be teleported. Can be the same as the current dimension key or a
	 *        different one
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @param z Z-Coordinate
	 * @param yaw Yaw
	 * @param pitch Pitch
	 * @return The teleported entity
	 */
	public static Entity teleportEntity(Entity entity, ResourceKey<Level> key, double x, double y, double z, float yaw, float pitch) {
		return teleportEntity(entity, getServerLevel(entity, key), x, y, z, yaw, pitch);
	}
	
	/**
	 * Teleports any entity to a given location in a given {@link ServerLevel}.
	 *
	 * @param entity The entity to teleport
	 * @param level The server level where the entity should be teleported. Can be the same as the current level or a
	 *        different one
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @param z Z-Coordinate
	 * @param yaw Yaw
	 * @param pitch Pitch
	 * @return The teleported entity
	 */
	public static Entity teleportEntity(Entity entity, ServerLevel level, double x, double y, double z, float yaw, float pitch) {
		return teleportEntity(entity, level, x, y, z, yaw, pitch, true);
	}
	
	/**
	 * Teleports any entity to a given location in a given {@link ServerLevel}.
	 *
	 * @param entity The entity to teleport
	 * @param level The server level where the entity should be teleported. Can be the same as the current level or a
	 *        different one
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @param z Z-Coordinate
	 * @param yaw Yaw
	 * @param pitch Pitch
	 * @param detach Detach the entity
	 * @return The teleported entity
	 */
	public static Entity teleportEntity(Entity entity, ServerLevel level, double x, double y, double z, float yaw, float pitch, boolean detach) {
		final float wrapedYaw = Mth.wrapDegrees(yaw);
		final float wrapedPitch = Mth.wrapDegrees(pitch);
		if (entity instanceof final ServerPlayer player) {
			level.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, new ChunkPos(new BlockPos(x, y, z)), 1, entity.getId());
			if (detach) {
				player.stopRiding();
			}
			if (player.isSleeping()) {
				player.stopSleepInBed(true, true);
			}
			if (level == entity.level) {
				player.connection.teleport(x, y, z, wrapedYaw, wrapedPitch);
			} else {
				player.teleportTo(level, x, y, z, wrapedYaw, wrapedPitch);
			}
			entity.setYHeadRot(wrapedYaw);
		} else {
			final float clampedPitch = Mth.clamp(wrapedPitch, -90F, 90F);
			if (level == entity.level) {
				entity.moveTo(x, y, z, wrapedYaw, clampedPitch);
				entity.setYHeadRot(wrapedYaw);
			} else {
				if (detach) {
					entity.unRide();
				}
				final Entity entityOld = entity;
				entity = entity.getType().create(level);
				if (entity == null) {
					return null;
				}
				entity.restoreFrom(entityOld);
				entity.moveTo(x, y, z, wrapedYaw, clampedPitch);
				entity.setYHeadRot(wrapedYaw);
				entityOld.setRemoved(Entity.RemovalReason.CHANGED_DIMENSION);
				level.addDuringTeleport(entity);
			}
		}
		
		if (!(entity instanceof final LivingEntity livingEntity) || !livingEntity.isFallFlying()) {
			entity.setDeltaMovement(entity.getDeltaMovement().multiply(1, 0, 1));
			entity.setOnGround(true);
		}
		
		if (entity instanceof final PathfinderMob pathFinderMob) {
			pathFinderMob.getNavigation().stop();
		}
		
		return entity;
	}
}
