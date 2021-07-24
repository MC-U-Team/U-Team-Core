package info.u_team.u_team_core.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;

import net.minecraft.world.item.Item.Properties;

public class UItem extends Item {
	
	public UItem(Properties properties) {
		this(null, properties);
	}
	
	public UItem(CreativeModeTab group, Properties properties) {
		super(group == null ? properties : properties.tab(group));
	}
	
}
