package info.u_team.u_team_core.util;

import java.util.List;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.Item.Properties;

public class ItemProperties extends Properties {
	
	public ItemProperties() {
	}
	
	public ItemProperties(Properties properties) {
		craftingRemainingItem = properties.craftingRemainingItem;
		requiredFeatures = properties.requiredFeatures;
		components = DataComponentMap.builder().addAll(properties.components.build());
		
		Extension.INSTANCES.forEach(extension -> extension.copy(this, properties));
	}
	
	public interface Extension {
		
		List<Extension> INSTANCES = ServiceUtil.loadAll(Extension.class);
		
		void copy(ItemProperties ourProperties, Properties properties);
	}
}
