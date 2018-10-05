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

package info.u_team.u_team_core.item.tool;

import com.google.common.collect.Multimap;

import info.u_team.u_team_core.api.IModelProvider;
import info.u_team.u_team_core.api.registry.IUItem;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.relauncher.*;

/**
 * Item API<br>
 * -> Basic Sword
 * 
 * @date 05.07.2018
 * @author HyCraftHD
 */
public class UItemSword extends ItemSword implements IUItem, IModelProvider {
	
	protected String name;
	
	protected double speedmodifier;
	
	public UItemSword(String name, ToolMaterial material) {
		this(name, null, material);
	}
	
	public UItemSword(String name, UCreativeTab tab, ToolMaterial material) {
		this(name, tab, material, -2.4000000953674316D);
	}
	
	public UItemSword(String name, ToolMaterial material, double speedmodifier) {
		this(name, null, material, speedmodifier);
	}
	
	public UItemSword(String name, UCreativeTab tab, ToolMaterial material, double speedmodifier) {
		super(material);
		
		this.name = name;
		this.speedmodifier = speedmodifier;
		
		if (tab != null) {
			setCreativeTab(tab);
		}
		
	}
	
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
		
		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", speedmodifier, 0));
		}
		
		return multimap;
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
