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

package info.u_team.u_team_core.intern.network.message;

import java.io.IOException;

import info.u_team.u_team_core.UCoreConstants;
import info.u_team.u_team_core.api.ISyncedContainerTileEntity;
import info.u_team.u_team_core.gui.UGuiContainerTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * Custom message for container and tileentity synchronization
 * 
 * @date 30.12.2018
 * @author HyCraftHD
 */
public class MessageSyncedContainer implements IMessage {
	
	private BlockPos pos;
	private NBTTagCompound compound;
	
	public MessageSyncedContainer() {
	}
	
	public MessageSyncedContainer(BlockPos pos, NBTTagCompound compound) {
		this.pos = pos;
		this.compound = compound;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer packetbuf = new PacketBuffer(buf);
		try {
			pos = packetbuf.readBlockPos();
			compound = packetbuf.readCompoundTag();
		} catch (IOException ex) {
			UCoreConstants.LOGGER.warn("Error in Synced Container packet decoding.", ex);
		}
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer packetbuf = new PacketBuffer(buf);
		packetbuf.writeBlockPos(pos);
		packetbuf.writeCompoundTag(compound);
	}
	
	private NBTTagCompound getCompound() {
		return compound;
	}
	
	private BlockPos getPos() {
		return pos;
	}
	
	public static class Handler implements IMessageHandler<MessageSyncedContainer, IMessage> {
		
		@Override
		public IMessage onMessage(MessageSyncedContainer message, MessageContext ctx) {
			if (ctx.side == Side.CLIENT) {
				handleClient(message.getPos(), message.getCompound(), ctx);
			} else {
				handleServer(message.getPos(), message.getCompound(), ctx);
			}
			return null;
		}
		
		@SideOnly(Side.CLIENT)
		private void handleClient(BlockPos pos, NBTTagCompound compound, MessageContext ctx) {
			Minecraft minecraft = Minecraft.getMinecraft();
			WorldClient world = minecraft.world;
			if (!world.isBlockLoaded(pos)) {
				return;
			}
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity instanceof ISyncedContainerTileEntity) {
				ISyncedContainerTileEntity synced = (ISyncedContainerTileEntity) tileentity;
				minecraft.addScheduledTask(() -> synced.handleFromServerSyncContainerData(compound));
			}
			GuiScreen gui = minecraft.currentScreen;
			if (gui instanceof UGuiContainerTileEntity) {
				UGuiContainerTileEntity guicontainer = (UGuiContainerTileEntity) gui;
				minecraft.addScheduledTask(() -> guicontainer.handleServerData(compound));
			}
		}
		
		private void handleServer(BlockPos pos, NBTTagCompound compound, MessageContext ctx) {
			World world = ctx.getServerHandler().player.getServerWorld();
			MinecraftServer server = world.getMinecraftServer();
			if (!world.isBlockLoaded(pos)) {
				return;
			}
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity instanceof ISyncedContainerTileEntity) {
				ISyncedContainerTileEntity synced = (ISyncedContainerTileEntity) tileentity;
				server.addScheduledTask(() -> synced.handleFromClientSyncContainerData(compound));
			}
		}
	}
}
