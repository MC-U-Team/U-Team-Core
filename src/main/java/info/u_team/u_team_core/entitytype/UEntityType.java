package info.u_team.u_team_core.entitytype;

import java.util.function.Function;

import info.u_team.u_team_core.api.registry.IUEntityType;
import net.minecraft.entity.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages.SpawnEntity;

public class UEntityType<T extends Entity> extends EntityType<T> implements IUEntityType {
	
	protected final String name;
	
	public UEntityType(String name, Class<? extends T> clazz, Function<? super World, ? extends T> factory, boolean serializable, boolean summonable, boolean useVanillaSpawning, Function<SpawnEntity, Entity> customSpawnCallback, boolean hasCustomTracking, int range, int updateFreq, boolean sendVelocityUpdates) {
		super(clazz, factory, serializable, summonable, null, useVanillaSpawning, customSpawnCallback, hasCustomTracking, range, updateFreq, sendVelocityUpdates);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
	public static class Builder<T extends Entity> {
		
		private final String name;
		private final Class<? extends T> entityClass;
		private final Function<? super World, ? extends T> factory;
		
		private boolean serializable = true;
		private boolean summonable = true;
		private Function<SpawnEntity, Entity> customSpawnCallback = null;
		private boolean hasCustomTracking = false;
		private int trackingRange;
		private int updateFrequency;
		private boolean sendVelocityUpdates;
		
		private Builder(String name, Class<? extends T> entityClass, Function<? super World, ? extends T> factory) {
			this.name = name;
			this.entityClass = entityClass;
			this.factory = factory;
		}
		
		public static <T extends Entity> Builder<T> create(String name, Class<? extends T> entityClass, Function<? super World, ? extends T> factory) {
			return new Builder<>(name, entityClass, factory);
		}
		
		public Builder<T> disableSummoning() {
			summonable = false;
			return this;
		}
		
		public Builder<T> disableSerialization() {
			serializable = false;
			return this;
		}
		
		public Builder<T> customSpawning(Function<SpawnEntity, Entity> function, boolean useVanillaSpawning) {
			customSpawnCallback = function;
			return this;
		}
		
		public Builder<T> tracker(int range, int updateFrequency, boolean sendVelocityUpdates) {
			this.hasCustomTracking = true;
			this.trackingRange = range;
			this.updateFrequency = updateFrequency;
			this.sendVelocityUpdates = sendVelocityUpdates;
			return this;
		}
		
		public UEntityType<T> build() {
			return new UEntityType<>(name, entityClass, factory, serializable, summonable, false, customSpawnCallback, hasCustomTracking, trackingRange, updateFrequency, sendVelocityUpdates);
		}
	}
	
}
