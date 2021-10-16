package info.u_team.u_team_core.util;

import java.lang.reflect.Field;

import net.minecraft.world.item.Item.Properties;

public class ItemProperties extends Properties {
	
	private static final Field CAN_REPAIR_FIELD = ReflectionUtil.findField(Properties.class, "canRepair");
	
	public ItemProperties() {
	}
	
	public ItemProperties(Properties properties) {
		maxStackSize = properties.maxStackSize;
		maxDamage = properties.maxDamage;
		craftingRemainingItem = properties.craftingRemainingItem;
		category = properties.category;
		rarity = properties.rarity;
		foodProperties = properties.foodProperties;
		isFireResistant = properties.isFireResistant;
		
		ReflectionUtil.copyValue(CAN_REPAIR_FIELD, properties, this);
	}
}
