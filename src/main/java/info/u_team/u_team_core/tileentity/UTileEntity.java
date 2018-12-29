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

package info.u_team.u_team_core.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.*;

/**
 * TileEntity API<br>
 * -> Basic TileEntity
 * 
 * @date 17.08.2017
 * @author MrTroble, HyCraftHD
 */
public abstract class UTileEntity extends TileEntity {
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		writeNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		readNBT(compound);
	}
	
	public abstract void readNBT(NBTTagCompound compound);
	
	public abstract void writeNBT(NBTTagCompound compound);
	
	// synchronization server client
	public void sendChangesToClient() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
		world.scheduleBlockUpdate(pos, blockType, 0, 0);
		markDirty();
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, -1, getUpdateTag());
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet) {
		handleUpdateTag(packet.getNbtCompound());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = new NBTTagCompound();
		getServerSyncData(compound);
		return compound;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound compound) {
		handleClientSyncData(compound);
	}
	
	public void getServerSyncData(NBTTagCompound compound) {
	}
	
	public void handleClientSyncData(NBTTagCompound compound) {
	}
	
}
