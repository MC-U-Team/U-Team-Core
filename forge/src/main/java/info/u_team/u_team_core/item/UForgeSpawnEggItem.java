package info.u_team.u_team_core.item;

import java.util.function.Supplier;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;

public class UForgeSpawnEggItem extends ForgeSpawnEggItem {
	
	public UForgeSpawnEggItem(Properties properties, Supplier<? extends EntityType<? extends Mob>> entityType, int backgroundColor, int highlightColor) {
		super(entityType, backgroundColor, highlightColor, properties);
	}
	
	public static class Creator implements SpawnEggCreator.Creator {
		
		@Override
		public SpawnEggItem create(Properties properties, Supplier<? extends EntityType<? extends Mob>> entityType, int backgroundColor, int highlightColor) {
			return new UForgeSpawnEggItem(properties, entityType, backgroundColor, highlightColor);
		}
	}
}
