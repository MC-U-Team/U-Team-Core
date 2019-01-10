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

import org.apache.commons.lang3.tuple.Pair;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.chunk.Chunk;

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
	
	public boolean openContainer(Object mod, int gui, World world, BlockPos pos, EntityPlayer player) {
		return openContainer(mod, gui, world, pos, player, false);
	}
	
	public boolean openContainer(Object mod, int gui, World world, BlockPos pos, EntityPlayer player, boolean canOpenSneak) {
		if (world.isRemote)
			// Need to return true here, cause else it will create two instances of our gui
			// which may cause bugs. The method onBlockActivated must return this value
			// correctly!
			return true;
		if (!isTileEntityFromProvider(world, pos).getLeft()) {
			return false;
		}
		if (!canOpenSneak && player.isSneaking()) {
			return true;
		}
		player.openGui(mod, gui, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	public TileEntity getTileEntitySave(IBlockAccess world, BlockPos pos) {
		return world instanceof ChunkCache ? ((ChunkCache) world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(pos); // Get tileentity without forcing to create one if its a chunkcache
	}
	
	public Pair<Boolean, TileEntity> isTileEntityFromProvider(IBlockAccess world, BlockPos pos) {
		TileEntity tileentity = world.getTileEntity(pos);
		boolean isValid = tileentity != null && provider.getTileEntityClass().isAssignableFrom(tileentity.getClass());
		return Pair.of(isValid, tileentity);
	}
	
}
