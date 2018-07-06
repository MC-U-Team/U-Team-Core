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
 * Block utility
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public class BlockUtil {
	
	public static Block from(Object obj) {
		Block block = null;
		if (obj instanceof ItemStack) {
			block = Block.getBlockFromItem(((ItemStack) obj).getItem());
		} else if (obj instanceof Item) {
			block = Block.getBlockFromItem((Item) obj);
		} else if (obj instanceof Block) {
			block = (Block) obj;
		} else if (obj instanceof String || obj instanceof Integer) {
			String s;
			if (obj instanceof Integer) {
				s = Integer.valueOf((String) obj).toString();
			} else {
				s = (String) obj;
			}
			block = Block.getBlockFromName(s);
		}
		return block;
	}
	
	public static ResourceLocation getRegistryName(Block block) {
		return block.getRegistryName();
	}
	
	public static String getModID(Block block) {
		return getRegistryName(block).getResourceDomain();
	}
	
	public static String getName(Block block) {
		return getRegistryName(block).getResourcePath();
		
	}
	
}
