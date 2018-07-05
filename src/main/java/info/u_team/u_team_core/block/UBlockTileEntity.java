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

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Block API<br>
 * -> Block for TileEntities
 * 
 * @date 17.08.2017
 * @author MrTroble, HyCraftHD
 */

public class UBlockTileEntity extends UBlock implements ITileEntityProvider {
	
	private UTileEntityProvider provider;
	
	public UBlockTileEntity(String name, Material material, UTileEntityProvider provider) {
		this(name, material, null, provider);
	}
	
	public UBlockTileEntity(String name, Material material, UCreativeTab tab, UTileEntityProvider provider) {
		super(name, material, tab);
		this.provider = provider;
		this.hasTileEntity = true;
	}
	
	@Override
	public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
		TileEntity tileentity = world.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return provider.create(world, meta);
	}
	
}
