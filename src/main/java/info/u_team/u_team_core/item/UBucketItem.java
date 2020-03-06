package info.u_team.u_team_core.item;

import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;

public class UBucketItem extends BucketItem implements IURegistryType {
	
	protected final String name;
	
	public UBucketItem(String name, Properties properties, Supplier<? extends Fluid> fluid) {
		this(name, null, properties, fluid);
	}
	
	public UBucketItem(String name, ItemGroup group, Properties properties, Supplier<? extends Fluid> fluid) {
		super(fluid, group == null ? properties : properties.group(group));
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
}
