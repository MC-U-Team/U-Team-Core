package info.u_team.u_team_core.item;

import java.util.function.Supplier;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.SpawnEggItem;

public class SpawnEggCreator {
	
	private static final Creator CREATOR = ServiceUtil.loadOne(Creator.class);
	
	public static SpawnEggItem create(Properties properties, Supplier<? extends EntityType<? extends Mob>> entityType, int backgroundColor, int highlightColor) {
		return CREATOR.create(properties, entityType, backgroundColor, highlightColor);
	}
	
	public static interface Creator {
		
		SpawnEggItem create(Properties properties, Supplier<? extends EntityType<? extends Mob>> entityType, int backgroundColor, int highlightColor);
	}
	
}
