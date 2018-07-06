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

package info.u_team.u_team_core.block;

import info.u_team.u_team_core.UCoreConstants;
import info.u_team.u_team_core.api.*;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.item.*;
import info.u_team.u_team_core.property.PropertyList;
import info.u_team.u_team_core.util.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.*;

/**
 * Block API<br>
 * -> Metadata Blocks
 * 
 * @date 26.06.2018
 * @author HyCraftHD
 */
public class UBlockMetaData extends UBlock {
	
	private IMetaType[] list;
	private MetaTypeWrapper[] wrapped;
	
	public final PropertyList<MetaTypeWrapper> property;
	private BlockStateContainer blockstate;
	
	// private List<MetaType> types;
	
	public UBlockMetaData(String name, Material material, IMetaType[] list) {
		this(name, material, null, list);
	}
	
	public UBlockMetaData(String name, Material material, UCreativeTab tab, IMetaType[] list) {
		super(name, material, tab);
		this.list = list;
		wrapped = new MetaTypeWrapper[list.length];
		
		for (int i = 0; i < list.length; i++) {
			wrapped[i] = new MetaTypeWrapper(list[i]);
		}
		
		property = PropertyList.create("meta", MetaTypeWrapper.class, wrapped);
		blockstate = new BlockStateContainer(this, property);
		setDefaultState(blockstate.getBaseState());
	}
	
	@Override
	public BlockStateContainer getBlockState() {
		return blockstate;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		if (MathUtil.isInRange(meta, 0, list.length - 1)) {
			return getDefaultState().withProperty(property, wrapped[meta]);
		} else {
			UCoreConstants.LOGGER.warn("Unsupported metadata value {} for property meta in UBlockMetaData ( {} )", meta, getRegistryName());
			return getDefaultState();
		}
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(property).getMetadata();
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
	
	@Override
	public UItemBlock getItemBlock() {
		return new UItemBlockMetaData(this, list);
	}
	
	@Override
	public void registerModel() {
		for (int i = 0; i < list.length; i++) {
			setModel(getItem(), i, new CustomResourceLocation(getRegistryName(), "/" + list[i].getName()));
		}
	}
	
	public IMetaType[] getList() {
		return list;
	}
	
	public MetaTypeWrapper[] getWrapped() {
		return wrapped;
	}
	
	/**
	 * Block API<br>
	 * -> Metadata Blocks (Wrapper)
	 * 
	 * @date 28.06.2018
	 * @author HyCraftHD
	 */
	public static class MetaTypeWrapper implements IPropertyList, Comparable<MetaTypeWrapper> {
		
		private IMetaType wrapped;
		
		public MetaTypeWrapper(IMetaType wrapped) {
			this.wrapped = wrapped;
		}
		
		@Override
		public String getName() {
			return wrapped.getName();
		}
		
		public int getMetadata() {
			return wrapped.getMetadata();
		}
		
		@Override
		public int compareTo(MetaTypeWrapper wrapper) {
			return getName().compareTo(wrapper.getName());
		}
		
		@Override
		public boolean equals(Object object) {
			return getName().equals(((MetaTypeWrapper) object).getName());
		}
		
		@Override
		public String toString() {
			return getName();
		}
		
		public IMetaType getWrapped() {
			return wrapped;
		}
	}
	
}
