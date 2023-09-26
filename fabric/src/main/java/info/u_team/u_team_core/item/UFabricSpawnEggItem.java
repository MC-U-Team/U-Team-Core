package info.u_team.u_team_core.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import info.u_team.u_team_core.api.Platform.Environment;
import info.u_team.u_team_core.event.SetupEvents;
import info.u_team.u_team_core.util.EnvironmentUtil;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;

public class UFabricSpawnEggItem extends SpawnEggItem {
	
	private static final List<UFabricSpawnEggItem> TO_REGISTER = new ArrayList<>();
	private static final Map<EntityType<? extends Mob>, UFabricSpawnEggItem> EGGS = Maps.newIdentityHashMap();
	
	private final Supplier<? extends EntityType<? extends Mob>> entityType;
	
	public UFabricSpawnEggItem(Properties properties, Supplier<? extends EntityType<? extends Mob>> entityType, int backgroundColor, int highlightColor) {
		super(null, backgroundColor, highlightColor, properties);
		BY_ID.remove(null, this);
		TO_REGISTER.add(this);
		this.entityType = Suppliers.memoize(entityType::get);
	}
	
	public EntityType<? extends Mob> getEntityType() {
		return entityType.get();
	}
	
	public DispenseItemBehavior createDispenserItemBehavior() {
		return new DefaultDispenseItemBehavior() {
			
			@Override
			public ItemStack execute(BlockSource source, ItemStack stack) {
				final Direction direction = source.state().getValue(DispenserBlock.FACING);
				final EntityType<?> entityType = ((UFabricSpawnEggItem) stack.getItem()).getType(stack.getTag());
				try {
					entityType.spawn(source.level(), stack, null, source.pos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
				} catch (final Exception ex) {
					LOGGER.error("Error while dispensing spawn egg from dispenser at {}", source.pos(), ex);
					return ItemStack.EMPTY;
				}
				stack.shrink(1);
				source.level().gameEvent(null, GameEvent.ENTITY_PLACE, source.pos());
				return stack;
			}
		};
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
	
	public static class Handler {
		
		public static void register() {
			SetupEvents.COMMON_SETUP.register(() -> {
				TO_REGISTER.forEach(spawnEgg -> {
					EGGS.put(spawnEgg.getEntityType(), spawnEgg);
					
					final DispenseItemBehavior behavior = spawnEgg.createDispenserItemBehavior();
					if (behavior != null) {
						DispenserBlock.registerBehavior(spawnEgg, behavior);
					}
					
					EnvironmentUtil.runWhen(Environment.CLIENT, () -> () -> Client.register(spawnEgg));
				});
			});
		}
		
		private static class Client {
			
			private static void register(UFabricSpawnEggItem spawnEgg) {
				ColorProviderRegistry.ITEM.register((stack, tintIndex) -> spawnEgg.getColor(tintIndex), spawnEgg);
			}
		}
	}
	
	public static class Creator implements SpawnEggCreator.Creator {
		
		@Override
		public SpawnEggItem create(Properties properties, Supplier<? extends EntityType<? extends Mob>> entityType, int backgroundColor, int highlightColor) {
			return new UFabricSpawnEggItem(properties, entityType, backgroundColor, highlightColor);
		}
	}
}
