package info.u_team.u_team_core.item;

import java.util.function.Supplier;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.Fluid;

public class UBucketItem extends BucketItem {
	
	public UBucketItem(Properties properties, Supplier<? extends Fluid> fluid) {
		super(fluid, properties);
	}
	
}
