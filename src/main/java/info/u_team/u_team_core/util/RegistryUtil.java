/*-*****************************************************************************
 * Copyright 2018 U-Team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package info.u_team.u_team_core.util;

import java.lang.reflect.Field;
import java.util.*;

import info.u_team.u_team_core.UCoreConstants;
import info.u_team.u_team_core.api.registry.IUEntityEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Registry utility
 * 
 * @author HyCraftHD
 * @date 24.06.2018
 */
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
	
	public static List<IUEntityEntry> getEntityRegistryEntries(Class<?> init) {
		List<IUEntityEntry> list = new ArrayList<>();
		try {
			for (Field field : init.getDeclaredFields()) {
				if (IUEntityEntry.class.isAssignableFrom(field.getType())) {
					list.add((IUEntityEntry) field.get(null));
				}
			}
		} catch (Exception ex) {
			UCoreConstants.LOGGER.error("Failed fetching entity registry entries for class {}", init, ex);
		}
		return list;
	}
	
}