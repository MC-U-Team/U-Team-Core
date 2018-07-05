/*******************************************************************************
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
package info.u_team.u_team_core.item;

import info.u_team.u_team_core.block.UBlock;
import net.minecraft.item.ItemBlock;

/**
 * ItemBlock API<br>
 * -> Basic ItemBlock
 * 
 * @date 17.08.2017
 * @author MrTroble
 */

public class UItemBlock extends ItemBlock {
	
	public UItemBlock(UBlock block) {
		super(block);
		setRegistryName(block.getRegistryName());
	}
	
}
