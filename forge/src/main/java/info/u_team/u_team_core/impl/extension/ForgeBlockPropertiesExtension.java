package info.u_team.u_team_core.impl.extension;

import java.lang.reflect.Field;

import info.u_team.u_team_core.util.BlockProperties;
import info.u_team.u_team_core.util.ReflectionUtil;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ForgeBlockPropertiesExtension implements BlockProperties.Extension {
	
	private static final Field LOOT_TABLE_SUPPLIER_FIELD = ReflectionUtil.findField(Properties.class, "lootTableSupplier");
	
	@Override
	public void copy(BlockProperties ourProperties, Properties properties) {
		ReflectionUtil.copyValue(LOOT_TABLE_SUPPLIER_FIELD, properties, ourProperties);
	}
	
}
