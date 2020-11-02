package info.u_team.u_team_core.item;

import java.util.*;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.util.LazyValue;

public class USpawnEggItem extends SpawnEggItem {
	
	public static final List<Pair<LazyValue<? extends EntityType<?>>, USpawnEggItem>> LAZY_EGGS = new ArrayList<>();
	
	public USpawnEggItem(Properties properties, Supplier<? extends EntityType<?>> entityType, int primaryColor, int secondaryColor) {
		this(null, properties, entityType, primaryColor, secondaryColor);
	}
	
	public USpawnEggItem(ItemGroup group, Properties properties, Supplier<? extends EntityType<?>> entityType, int primaryColor, int secondaryColor) {
		super(null, primaryColor, secondaryColor, group == null ? properties : properties.group(group));
		EGGS.remove(null);
		LAZY_EGGS.add(Pair.of(new LazyValue<>(entityType), this));
	}
	
}
