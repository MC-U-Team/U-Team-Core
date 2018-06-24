package info.u_team.u_team_core.util;

import java.lang.reflect.Field;
import java.util.*;

import info.u_team.u_team_core.intern.UCoreConstants;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryUtil {
	
	@SuppressWarnings("unchecked")
	public static <T extends IForgeRegistryEntry<T>> List<T> getRegistryEntries(Class<T> clazz, Class<?> init) {
		List<T> list = new ArrayList<>();
		try {
			for (Field field : init.getDeclaredFields()) {
				if (clazz.isAssignableFrom(field.getType())) {
					list.add((T) field.get(null));
				}
			}
		} catch (Exception ex) {
			UCoreConstants.LOGGER.error("Failed fetching registry entries for class {}", init, ex);
		}
		return list;
	}
	
}
