package info.u_team.u_team_core.entitytype;

import java.util.function.*;

import info.u_team.u_team_core.api.registry.IUEntityType;
import net.minecraft.entity.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages.SpawnEntity;

public class UEntityType<T extends Entity> extends EntityType<T> implements IUEntityType {
	
	protected final String name;
	
	public UEntityType(String name, IFactory<T> factory, EntityClassification classification, boolean serializable, boolean summonable, boolean immuneToFire, EntitySize size, Predicate<EntityType<?>> velocityUpdateSupplier, ToIntFunction<EntityType<?>> trackingRangeSupplier, ToIntFunction<EntityType<?>> updatetervalSupplier, BiFunction<SpawnEntity, World, T> customClientFactory) {
		super(factory, classification, serializable, summonable, immuneToFire, null, size, velocityUpdateSupplier, trackingRangeSupplier, updatetervalSupplier, customClientFactory);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
	public static class Builder<T extends Entity> {
		
		private final String name;
		private final IFactory<T> factory;
		private final EntityClassification classification;
		private boolean serializable = true;
		private boolean summonable = true;
		private boolean immuneToFire;
		private Predicate<EntityType<?>> velocityUpdateSupplier = type -> true;
		private ToIntFunction<EntityType<?>> trackingRangeSupplier = type -> 5;
		private ToIntFunction<EntityType<?>> updateIntervalSupplier = type -> 3;
		private BiFunction<SpawnEntity, World, T> customClientFactory;
		
		private EntitySize size = EntitySize.flexible(0.6F, 1.8F);
		
		private Builder(String name, IFactory<T> factory, EntityClassification classification) {
			this.name = name;
			this.factory = factory;
			this.classification = classification;
		}
		
		public static <T extends Entity> Builder<T> create(String name, IFactory<T> factory, EntityClassification classification) {
			return new Builder<>(name, factory, classification);
		}
		
		public static <T extends Entity> Builder<T> create(String name, EntityClassification classification) {
			return new Builder<>(name, (type, world) -> null, classification);
		}
		
		public Builder<T> size(float width, float height) {
			this.size = EntitySize.flexible(width, height);
			return this;
		}
		
		public Builder<T> disableSummoning() {
			this.summonable = false;
			return this;
		}
		
		public Builder<T> disableSerialization() {
			this.serializable = false;
			return this;
		}
		
		public Builder<T> immuneToFire() {
			this.immuneToFire = true;
			return this;
		}
		
		public Builder<T> setUpdateInterval(int interval) {
			this.updateIntervalSupplier = t -> interval;
			return this;
		}
		
		public Builder<T> setTrackingRange(int range) {
			this.trackingRangeSupplier = t -> range;
			return this;
		}
		
		public Builder<T> setShouldReceiveVelocityUpdates(boolean value) {
			this.velocityUpdateSupplier = t -> value;
			return this;
		}
		
		public Builder<T> setCustomClientFactory(BiFunction<SpawnEntity, World, T> customClientFactory) {
			this.customClientFactory = customClientFactory;
			return this;
		}
		
		public EntityType<T> build() {
			return new UEntityType<>(name, factory, classification, serializable, summonable, immuneToFire, size, velocityUpdateSupplier, trackingRangeSupplier, updateIntervalSupplier, customClientFactory);
		}
	}
}
