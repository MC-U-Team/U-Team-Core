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

package info.u_team.u_team_core.util.stack;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;

/**
 * Item utility
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public class ItemUtil {
	
	public static Item from(Object obj) {
		Item item = null;
		if (obj instanceof ItemStack) {
			item = ((ItemStack) obj).getItem();
		} else if (obj instanceof Item) {
			item = (Item) obj;
		} else if (obj instanceof Block) {
			item = Item.getItemFromBlock((Block) obj);
		} else if (obj instanceof String || obj instanceof Integer) {
			String s;
			if (obj instanceof Integer) {
				s = Integer.valueOf((String) obj).toString();
			} else {
				s = (String) obj;
			}
			Block block = Block.getBlockFromName(s);
			item = Item.getByNameOrId(s);
			if (block != null) {
				item = Item.getItemFromBlock(block);
			}
		}
		return item;
	}
	
	public static ResourceLocation getRegistryName(Item item) {
		return item.getRegistryName();
	}
	
	public static String getModID(Item item) {
		return getRegistryName(item).getNamespace();
	}
	
	public static String getName(Item item) {
		return getRegistryName(item).getPath();
	}
	
}
