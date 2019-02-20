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

package info.u_team.u_team_core.api;

import info.u_team.u_team_core.container.UContainerTileEntity;
import info.u_team.u_team_core.intern.init.UCoreNetwork;
import info.u_team.u_team_core.intern.network.MessageSyncedContainer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.network.PacketDistributor;

/**
 * Implement this in your own {@link TileEntity} and use
 * {@link UContainerTileEntity} for synchronization
 * 
 * @date 30.12.2018
 * @author HyCraftHD
 */
public interface ISyncedContainerTileEntity {
	
	public void getServerSyncContainerData(NBTTagCompound compound);
	
	@OnlyIn(Dist.CLIENT)
	public void handleFromServerSyncContainerData(NBTTagCompound compound);
	
	@OnlyIn(Dist.CLIENT)
	public void getClientSyncContainerData(NBTTagCompound compound);
	
	public void handleFromClientSyncContainerData(NBTTagCompound compound);
	
	public default void syncServerToClient(EntityPlayerMP player, BlockPos pos) {
		NBTTagCompound compound = new NBTTagCompound();
		getClientSyncContainerData(compound);
		sendMessageToClient(player, pos, compound);
	}
	
	@OnlyIn(Dist.CLIENT)
	public default void syncClientToServer(BlockPos pos) {
		NBTTagCompound compound = new NBTTagCompound();
		getClientSyncContainerData(compound);
		sendMessageToServer(pos, compound);
	}
	
	// This method access internal stuff. DO NOT OVERWRITE
	public default void sendMessageToClient(EntityPlayerMP player, BlockPos pos, NBTTagCompound compound) {
		UCoreNetwork.network.send(PacketDistributor.PLAYER.with(() -> player), new MessageSyncedContainer(pos, compound));
	}
	
	// This method access internal stuff. DO NOT OVERWRITE
	@OnlyIn(Dist.CLIENT)
	public default void sendMessageToServer(BlockPos pos, NBTTagCompound compound) {
		UCoreNetwork.network.sendToServer(new MessageSyncedContainer(pos, compound));
	}
	
}
