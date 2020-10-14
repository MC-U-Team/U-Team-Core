package info.u_team.u_team_core.item;

import net.minecraft.item.*;

public class UItem extends Item {
	
	public UItem(Properties properties) {
		this(null, properties);
	}
	
	public UItem(ItemGroup group, Properties properties) {
		super(group == null ? properties : properties.group(group));
	}
	
}