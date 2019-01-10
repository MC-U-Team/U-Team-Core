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

package info.u_team.u_team_core.container;

import info.u_team.u_team_core.api.ISyncedContainerTileEntity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Container API<br>
 * -> Advanced Synced TileEntity Container<br>
 * See {@link ISyncedContainerTileEntity}
 * 
 * @date 30.12.2018
 * @author HyCraftHD
 */
public class UContainerTileEntity extends UContainer {
	
	protected final TileEntity tileentity;
	
	private NBTTagCompound lastCompound;
	
	public UContainerTileEntity(TileEntity tileentity) {
		this.tileentity = tileentity;
	}
	
	public TileEntity getTileentity() {
		return tileentity;
	}
	
	// Sync
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		if (listener instanceof EntityPlayerMP && tileentity instanceof ISyncedContainerTileEntity) {
			ISyncedContainerTileEntity synced = (ISyncedContainerTileEntity) tileentity;
			if (lastCompound == null) {
				lastCompound = new NBTTagCompound();
				synced.getServerSyncContainerData(lastCompound);
			}
			synced.sendMessageToClient((EntityPlayerMP) listener, tileentity.getPos(), lastCompound);
		}
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (tileentity instanceof ISyncedContainerTileEntity) {
			ISyncedContainerTileEntity synced = (ISyncedContainerTileEntity) tileentity;
			
			NBTTagCompound compound = new NBTTagCompound();
			synced.getServerSyncContainerData(compound);
			if (compound.equals(lastCompound)) {
				return;
			}
			lastCompound = compound;
			listeners.stream().filter(listener -> listener instanceof EntityPlayerMP).map(listener -> (EntityPlayerMP) listener).forEach(player -> {
				synced.sendMessageToClient(player, tileentity.getPos(), compound);
			});
		}
	}
}
