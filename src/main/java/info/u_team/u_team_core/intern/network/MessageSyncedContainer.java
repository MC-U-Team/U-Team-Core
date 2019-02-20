package info.u_team.u_team_core.intern.network;

import java.util.function.Supplier;

import info.u_team.u_team_core.api.ISyncedContainerTileEntity;
import info.u_team.u_team_core.gui.UGuiContainerTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageSyncedContainer {
	
	private BlockPos pos;
	private NBTTagCompound compound;
	
	public MessageSyncedContainer(BlockPos pos, NBTTagCompound compound) {
		this.pos = pos;
		this.compound = compound;
	}
	
	public static void encode(MessageSyncedContainer msg, PacketBuffer buf) {
		buf.writeBlockPos(msg.pos);
		buf.writeCompoundTag(msg.compound);
	}
	
	public static MessageSyncedContainer decode(PacketBuffer buf) {
		BlockPos pos = buf.readBlockPos();
		NBTTagCompound compound = buf.readCompoundTag();
		return new MessageSyncedContainer(pos, compound);
	}
	
	public static class Handler {
		
		public static void handle(MessageSyncedContainer message, Supplier<NetworkEvent.Context> ctxSupplier) {
			Context ctx = ctxSupplier.get();
			if (ctx.getDirection().getLogicalSide() == LogicalSide.CLIENT) {
				handleClient(message.pos, message.compound, ctx);
			} else {
				handleServer(message.pos, message.compound, ctx);
			}
		}
		
		@OnlyIn(Dist.CLIENT)
		private static void handleClient(BlockPos pos, NBTTagCompound compound, Context ctx) {
			Minecraft minecraft = Minecraft.getInstance();
			WorldClient world = minecraft.world;
			if (!world.isBlockLoaded(pos)) {
				return;
			}
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity instanceof ISyncedContainerTileEntity) {
				ISyncedContainerTileEntity synced = (ISyncedContainerTileEntity) tileentity;
				minecraft.addScheduledTask(() -> synced.handleFromServerSyncContainerData(compound));
			}
			minecraft.addScheduledTask(() -> {
				GuiScreen gui = minecraft.currentScreen;
				if (gui instanceof UGuiContainerTileEntity) {
					UGuiContainerTileEntity guicontainer = (UGuiContainerTileEntity) gui;
					guicontainer.handleServerDataInstant(compound);
					minecraft.addScheduledTask(() -> guicontainer.handleServerData(compound)); // Send it to gui the next tick (This might not be the best method)
				}
			});
		}
		
		private static void handleServer(BlockPos pos, NBTTagCompound compound, Context ctx) {
			EntityPlayerMP player = ctx.getSender();
			World world = player.getServerWorld();
			MinecraftServer server = world.getServer();
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
