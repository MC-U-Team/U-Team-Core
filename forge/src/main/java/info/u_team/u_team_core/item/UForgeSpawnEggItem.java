package info.u_team.u_team_core.item;

import java.util.function.Supplier;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.ForgeSpawnEggItem;

public class UForgeSpawnEggItem extends ForgeSpawnEggItem {
	
	public UForgeSpawnEggItem(Properties properties, Supplier<? extends EntityType<? extends Mob>> entityType, int primaryColor, int secondaryColor) {
		super(entityType, primaryColor, secondaryColor, properties);
	}
	
}
