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
package info.u_team.u_team_core.block;

import info.u_team.u_team_core.api.IModelProvider;
import info.u_team.u_team_core.api.registry.IUBlock;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.item.UItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.*;

/**
 * Block API<br>
 * -> Basic Block
 * 
 * @date 17.08.2017
 * @author MrTroble, HyCraftHD
 */

public class UBlock extends Block implements IUBlock, IModelProvider {
	
	protected String name;
	
	public UBlock(String name, Material material) {
		this(name, material, null);
	}
	
	public UBlock(String name, Material material, UCreativeTab tab) {
		super(material);
		
		this.name = name;
		
		if (tab != null) {
			setCreativeTab(tab);
		}
	}
	
	@Override
	public Item getItem() {
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public UItemBlock getItemBlock() {
		return new UItemBlock(this);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		setModel(getItem(), 0, getRegistryName());
	}
	
}
