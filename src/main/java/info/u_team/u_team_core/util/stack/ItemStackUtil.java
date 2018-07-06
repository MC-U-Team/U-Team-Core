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

import net.minecraft.item.*;

/**
 * ItemStack utility
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public class ItemStackUtil {
	
	public static ItemStack from(Object obj) {
		if (obj instanceof ItemStack) {
			return (ItemStack) obj;
		}
		Item item = ItemUtil.from(obj);
		return item != null ? new ItemStack(item) : ItemStack.EMPTY;
	}
}
