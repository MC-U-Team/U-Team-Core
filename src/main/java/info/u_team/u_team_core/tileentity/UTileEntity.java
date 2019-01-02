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
	
	// synchronization server -> client
	// https://mcforge.readthedocs.io/en/latest/tileentities/tileentity/#synchronizing-the-data-to-the-client
	
	// synchronization on chunk load
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = super.getUpdateTag();
		getChunkLoadServerSyncData(compound);
		return compound;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void handleUpdateTag(NBTTagCompound compound) {
		super.handleUpdateTag(compound);
		handleChunkLoadClientSyncData(compound);
	}
	
	private void getChunkLoadServerSyncData(NBTTagCompound compound) {
	}
	
	@SideOnly(Side.CLIENT)
	private void handleChunkLoadClientSyncData(NBTTagCompound compound) {
	}
	
	// synchronization on block update
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound compound = new NBTTagCompound();
		getServerSyncData(compound);
		if (!compound.isEmpty()) {
			return new SPacketUpdateTileEntity(pos, -1, compound);
		}
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet) {
		handleClientSyncData(packet.getNbtCompound());
	}
	
	public void getServerSyncData(NBTTagCompound compound) {
	}
	
	@SideOnly(Side.CLIENT)
	public void handleClientSyncData(NBTTagCompound compound) {
	}
	
	public void sendChangesToClient() {
		sendChangesToClient(2);
	}
	
	public void sendChangesToClient(int flags) {
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, flags);
	}
}
