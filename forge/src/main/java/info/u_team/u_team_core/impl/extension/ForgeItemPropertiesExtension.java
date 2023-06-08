package info.u_team.u_team_core.impl.extension;

import java.lang.reflect.Field;

import info.u_team.u_team_core.util.ItemProperties;
import info.u_team.u_team_core.util.ReflectionUtil;
import net.minecraft.world.item.Item.Properties;

public class ForgeItemPropertiesExtension implements ItemProperties.Extension {
	
	private static final Field CAN_REPAIR_FIELD = ReflectionUtil.findField(Properties.class, "canRepair");
	
	@Override
	public void copy(ItemProperties ourProperties, Properties properties) {
		ReflectionUtil.copyValue(CAN_REPAIR_FIELD, properties, ourProperties);
	}
	
}
