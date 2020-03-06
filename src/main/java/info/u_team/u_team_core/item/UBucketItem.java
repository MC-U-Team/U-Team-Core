package info.u_team.u_team_core.item;

import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;

public class UBucketItem extends BucketItem implements IURegistryType {
	
	protected final String name;
	
	public UBucketItem(String name, Properties builder, Supplier<? extends Fluid> supplier) {
		super(supplier, builder);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
}
