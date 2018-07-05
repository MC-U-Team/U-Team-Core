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
package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IModelProvider;
import info.u_team.u_team_core.api.registry.IUItem;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import net.minecraft.item.ItemAxe;
import net.minecraftforge.fml.relauncher.*;

/**
 * Item API<br>
 * -> Basic Axe
 * 
 * @date 05.07.2018
 * @author HyCraftHD
 */

public class UItemAxe extends ItemAxe implements IUItem, IModelProvider {
	
	protected String name;
	
	public UItemAxe(String name, ToolMaterial material) {
		this(name, null, material);
	}
	
	public UItemAxe(String name, ToolMaterial material, float speed) {
		this(name, null, material, speed);
	}
	
	public UItemAxe(String name, UCreativeTab tab, ToolMaterial material) {
		this(name, tab, material, -3.0F);
	}
	
	public UItemAxe(String name, UCreativeTab tab, ToolMaterial material, float speed) {
		this(name, tab, material, material.getAttackDamage(), speed);
	}
	
	public UItemAxe(String name, ToolMaterial material, float damage, float speed) {
		this(name, null, material, damage, speed);
	}
	
	public UItemAxe(String name, UCreativeTab tab, ToolMaterial material, float damage, float speed) {
		super(material, damage, speed);
		
		this.name = name;
		
		if (tab != null) {
			setCreativeTab(tab);
		}
		
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		setModel(this, 0, getRegistryName());
	}
}
