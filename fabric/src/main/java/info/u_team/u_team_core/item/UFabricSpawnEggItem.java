package info.u_team.u_team_core.item;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.SpawnEggItem;

public class UFabricSpawnEggItem extends SpawnEggItem {
	
	private static final Map<EntityType<? extends Mob>, UFabricSpawnEggItem> EGGS = Maps.newIdentityHashMap();
	
	private final Supplier<? extends EntityType<? extends Mob>> entityType;
	
	public UFabricSpawnEggItem(Properties properties, Supplier<? extends EntityType<? extends Mob>> entityType, int backgroundColor, int highlightColor) {
		super(null, backgroundColor, highlightColor, properties);
		BY_ID.remove(null, this);
		this.entityType = Suppliers.memoize(entityType::get);
	}
	
	public EntityType<? extends Mob> getEntityType() {
		return entityType.get();
	}
	
	public static UFabricSpawnEggItem getEgg(EntityType<?> type) {
		return EGGS.get(type);
	}
	
	public static Iterable<SpawnEggItem> getEggs() {
		return Iterables.unmodifiableIterable(EGGS.values());
	}
	
	@Override
	public EntityType<?> getType(CompoundTag nbt) {
		final EntityType<?> type = super.getType(nbt);
		return type != null ? type : getEntityType();
	}
	
	@Override
	public FeatureFlagSet requiredFeatures() {
		return getEntityType().requiredFeatures();
	}
	
}
