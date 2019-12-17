package info.u_team.u_team_core.item;

import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraft.item.*;

public class UItem extends Item implements IURegistryType {
	
	protected final String name;
	
	public UItem(String name, Properties properties) {
		this(name, null, properties);
	}
	
	public UItem(String name, ItemGroup group, Properties properties) {
		super(group == null ? properties : properties.group(group));
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
}
