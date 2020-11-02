package info.u_team.u_team_core.item;

import java.util.*;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.LazyValue;

public class USpawnEggItem extends SpawnEggItem {
	
	private static final List<Pair<LazyValue<? extends EntityType<?>>, USpawnEggItem>> LAZY_EGGS = new ArrayList<>();
	
	public USpawnEggItem(Supplier<? extends EntityType<?>> entityType, int primaryColor, int secondaryColor, Properties builder) {
		super(null, primaryColor, secondaryColor, builder);
		EGGS.remove(null);
		LAZY_EGGS.add(Pair.of(new LazyValue<>(entityType), this));
	}
	
//	public static List<Pair<LazyValue<? extends EntityType<?>>, USpawnEggItem>> getLazyEggs() {
//		return LAZY_EGGS;
//	}
	
}
