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

package info.u_team.u_team_core.creativetab;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.*;

/**
 * CreativeTab API<br>
 * -> Basic CreativeTab
 * 
 * @author MrTroble, HyCraftHD
 * @date 17.08.2017
 */

public class UCreativeTab extends CreativeTabs {
	
	private Item item = null;
	private int metadata = 0;
	
	public UCreativeTab(String modid, String name) {
		this(new ResourceLocation(modid, name));
	}
	
	public UCreativeTab(ResourceLocation location) {
		super(location.toString());
	}
	
	public void setIcon(Block block) {
		setIcon(block, 0);
	}
	
	public void setIcon(Block block, int metadata) {
		setIcon(Item.getItemFromBlock(block), metadata);
	}
	
	public void setIcon(Item item) {
		setIcon(item, 0);
	}
	
	public void setIcon(Item item, int metadata) {
		this.item = item;
		this.metadata = metadata;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getTabIconItem() {
		if (item == null) {
			return new ItemStack(Items.ACACIA_BOAT);
		}
		return new ItemStack(item, 1, metadata);
	}
	
}
