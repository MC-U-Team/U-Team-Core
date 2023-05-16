package info.u_team.u_team_core.util;

import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class ItemProperties extends Properties {
	
	public ItemProperties() {
	}
	
	public ItemProperties(Properties properties) {
		maxStackSize = properties.maxStackSize;
		maxDamage = properties.maxDamage;
		craftingRemainingItem = properties.craftingRemainingItem;
		rarity = properties.rarity;
		foodProperties = properties.foodProperties;
		isFireResistant = properties.isFireResistant;
		requiredFeatures = properties.requiredFeatures;
		
		Extension.INSTANCES.forEach(extension -> extension.copy(this, properties));
	}
	
	public interface Extension {
		
		List<Extension> INSTANCES = ServiceUtil.loadAll(Extension.class);
		
		void copy(ItemProperties ourProperties, Properties properties);
	}
}
