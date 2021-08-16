package info.u_team.u_team_core.util;

import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class ItemProperties extends Properties {

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
		setValueCanRepair(getValueCanRepair(properties));
	}

	private boolean getValueCanRepair(Properties properties) {
		return ObfuscationReflectionHelper.getPrivateValue(Properties.class, properties, "canRepair");
	}

	private void setValueCanRepair(boolean value) {
		ObfuscationReflectionHelper.setPrivateValue(Properties.class, this, value, "canRepair");
	}
}
