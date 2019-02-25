package info.u_team.u_team_core.util;

import com.google.common.collect.Maps;

import net.minecraft.item.Item.Properties;

public class ItemProperties extends Properties {
	
	public ItemProperties() {
	}
	
	public ItemProperties(Properties properties) {
		maxStackSize = properties.maxStackSize;
		maxDamage = properties.maxDamage;
		containerItem = properties.containerItem;
		group = properties.group;
		rarity = properties.rarity;
		canRepair = properties.canRepair;
		toolClasses = Maps.newHashMap(properties.toolClasses);
		teisr = properties.teisr;
	}
	
}
