package info.u_team.u_team_core.entitytype;

import java.lang.reflect.Method;
import java.util.function.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraft.entity.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.network.FMLPlayMessages.SpawnEntity;

public class UEntityType<T extends Entity> extends EntityType<T> implements IURegistryType {
	
	protected final String name;
	
	protected final BiFunction<EntityType<T>, World, T> clientFactory;
	
	protected UEntityType(String name, IFactory<T> factory, EntityClassification classification, boolean serializable, boolean summonable, boolean immuneToFire, boolean spawnable, EntitySize size, Predicate<EntityType<?>> velocityUpdateSupplier, ToIntFunction<EntityType<?>> trackingRangeSupplier, ToIntFunction<EntityType<?>> updateIntervalSupplier, BiFunction<EntityType<T>, World, T> clientFactory) {
		super(factory, classification, serializable, summonable, immuneToFire, spawnable, size, velocityUpdateSupplier, trackingRangeSupplier, updateIntervalSupplier, null);
		this.name = name;
		this.clientFactory = clientFactory;
	}
	
	@Override
	public T customClientSpawn(SpawnEntity packet, World world) {
		if (clientFactory != null) {
			return clientFactory.apply(this, world);
		}
		return super.customClientSpawn(packet, world);
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
	public static class UBuilder<T extends Entity> {
		
		private static final Method DEFAULT_VELOCITY_SUPPLIER_METHOD = ObfuscationReflectionHelper.findMethod(EntityType.class, "defaultVelocitySupplier");
		private static final Method DEFAULT_TRACKING_RANGE_SUPPLIER_METHOD = ObfuscationReflectionHelper.findMethod(EntityType.class, "defaultTrackingRangeSupplier");
		private static final Method DEFAULT_UPDATE_INTERVAL_SUPPLIER_METHOD = ObfuscationReflectionHelper.findMethod(EntityType.class, "defaultUpdateIntervalSupplier");
		
		private final String name;
		private final IFactory<T> factory;
		private final EntityClassification classification;
		private boolean serializable = true;
		private boolean summonable = true;
		private boolean immuneToFire;
		private Predicate<EntityType<?>> velocityUpdateSupplier;// = EntityType::defaultVelocitySupplier;
		private ToIntFunction<EntityType<?>> trackingRangeSupplier;// = EntityType::defaultTrackingRangeSupplier;
		private ToIntFunction<EntityType<?>> updateIntervalSupplier;// = EntityType::defaultUpdateIntervalSupplier;
		private BiFunction<EntityType<T>, World, T> clientFactory;
		private boolean spawnable;
		
		private EntitySize size = EntitySize.flexible(0.6F, 1.8F);
		
		protected UBuilder(String name, IFactory<T> factory, EntityClassification classification) {
			this.name = name;
			this.factory = factory;
			this.clientFactory = factory::create;
			this.classification = classification;
		}
		
		public static <T extends Entity> UBuilder<T> create(String name, IFactory<T> factory, EntityClassification classification) {
			return new UBuilder<>(name, factory, classification);
		}
		
		public UBuilder<T> size(float width, float height) {
			size = EntitySize.flexible(width, height);
			return this;
		}
		
		public UBuilder<T> disableSummoning() {
			summonable = false;
			return this;
		}
		
		public UBuilder<T> disableSerialization() {
			serializable = false;
			return this;
		}
		
		public UBuilder<T> immuneToFire() {
			immuneToFire = true;
			return this;
		}
		
		public UBuilder<T> setUpdateInterval(int interval) {
			updateIntervalSupplier = type -> interval;
			return this;
		}
		
		public UBuilder<T> setTrackingRange(int range) {
			trackingRangeSupplier = type -> range;
			return this;
		}
		
		public UBuilder<T> setShouldReceiveVelocityUpdates(boolean value) {
			velocityUpdateSupplier = type -> value;
			return this;
		}
		
		public UBuilder<T> setCustomClientFactory(BiFunction<EntityType<T>, World, T> clientFactory) {
			this.clientFactory = clientFactory;
			return this;
		}
		
		public UBuilder<T> setSpawnable() {
			this.spawnable = true;
			return this;
		}
		
		public UEntityType<T> build() {
			if (velocityUpdateSupplier == null) {
				velocityUpdateSupplier = type -> getPrivateDefaultValue(type, DEFAULT_VELOCITY_SUPPLIER_METHOD);
			}
			if (trackingRangeSupplier == null) {
				trackingRangeSupplier = type -> getPrivateDefaultValue(type, DEFAULT_TRACKING_RANGE_SUPPLIER_METHOD);
			}
			if (updateIntervalSupplier == null) {
				updateIntervalSupplier = type -> getPrivateDefaultValue(type, DEFAULT_UPDATE_INTERVAL_SUPPLIER_METHOD);
			}
			return new UEntityType<>(name, factory, classification, serializable, summonable, immuneToFire, spawnable, size, velocityUpdateSupplier, trackingRangeSupplier, updateIntervalSupplier, clientFactory);
		}
		
		/**
		 * Invokes a method in {@link EntityType} with the only parameter {@link EntityType}.
		 * 
		 * @param <D> The type of the return value
		 * @param type The entity type instance and the argument
		 * @param method The method
		 * @return The methods return value
		 */
		@SuppressWarnings("unchecked")
		private <D> D getPrivateDefaultValue(EntityType<?> type, Method method) {
			try {
				return (D) method.invoke(type);
			} catch (Exception ex) {
				UCoreMain.LOGGER.error("Could not get default values for entity type {}.", name, ex);
			}
			return null;
		}
	}
}
