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

package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.api.registry.IUEntityEntry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Entity API<br>
 * -> Registry
 * 
 * @date 05.07.2018
 * @author HyCraftHD
 */

public class EntityRegistry {
	
	static List<EntityEntry> entities = new ArrayList<>();
	
	public static void register(String modid, IUEntityEntry entry) {
		entities.add(entry.getEntry());
	}
	
	public static void register(String modid, Collection<IUEntityEntry> list) {
		list.forEach(entity -> register(modid, entity));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<EntityEntry> event) {
		IForgeRegistry<EntityEntry> registry = event.getRegistry();
		entities.forEach(registry::register);
	}
	
}
