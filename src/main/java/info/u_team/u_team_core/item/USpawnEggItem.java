package info.u_team.u_team_core.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.util.LazyLoadedValue;

import net.minecraft.world.item.Item.Properties;

public class USpawnEggItem extends SpawnEggItem {
	
	public static final List<Pair<LazyLoadedValue<? extends EntityType<?>>, USpawnEggItem>> LAZY_EGGS = new ArrayList<>();
	
	public USpawnEggItem(Properties properties, Supplier<? extends EntityType<?>> entityType, int primaryColor, int secondaryColor) {
		this(null, properties, entityType, primaryColor, secondaryColor);
	}
	
	public USpawnEggItem(CreativeModeTab group, Properties properties, Supplier<? extends EntityType<?>> entityType, int primaryColor, int secondaryColor) {
		super(null, primaryColor, secondaryColor, group == null ? properties : properties.tab(group));
		BY_ID.remove(null);
		LAZY_EGGS.add(Pair.of(new LazyLoadedValue<>(entityType), this));
	}
	
}
