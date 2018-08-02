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

import info.u_team.u_team_core.api.registry.IUItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Item API<br>
 * -> Registry
 * 
 * @date 05.07.2018
 * @author HyCraftHD
 */

public class ItemRegistry {
	
	static List<Item> items = new ArrayList<>();
	
	public static void register(String modid, Item item) {
		if (item instanceof IUItem) {
			IUItem iuitem = (IUItem) item;
			item.setRegistryName(modid, iuitem.getName());
			item.setTranslationKey(modid + ":" + iuitem.getName());
		}
		items.add(item);
	}
	
	public static void register(String modid, Collection<Item> list) {
		list.forEach(item -> register(modid, item));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		items.forEach(registry::register);
	}
	
}
