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

package info.u_team.u_team_core.item;

import info.u_team.u_team_core.api.IMetaType;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.util.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * Item API<br>
 * -> Metadata Items
 * 
 * @date 26.06.2018
 * @author HyCraftHD
 */
public class UItemMetaData extends UItem {
	
	private IMetaType[] list;
	
	public UItemMetaData(String name, IMetaType[] list) {
		this(name, null, list);
	}
	
	public UItemMetaData(String name, UCreativeTab tab, IMetaType[] list) {
		super(name, tab);
		this.list = list;
		setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int metadata = stack.getMetadata();
		if (MathUtil.isInRange(metadata, 0, list.length - 1)) {
			return getUnlocalizedName() + "." + list[metadata].getName();
		}
		return getUnlocalizedName();
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab)) {
			for (int i = 0; i < list.length; i++) {
				items.add(new ItemStack(this, 1, list[i].getMetadata()));
			}
		}
	}
	
	@Override
	public void registerModel() {
		for (int i = 0; i < list.length; i++) {
			setModel(this, i, new CustomResourceLocation(getRegistryName(), "/" + list[i].getName()));
		}
	}
	
	public IMetaType[] getList() {
		return list;
	}
	
}
