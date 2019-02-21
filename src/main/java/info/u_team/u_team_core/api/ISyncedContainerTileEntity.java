package info.u_team.u_team_core.api;

import info.u_team.u_team_core.intern.init.UCoreNetwork;
import info.u_team.u_team_core.intern.network.MessageSyncedContainer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.network.PacketDistributor;

public interface ISyncedContainerTileEntity {
	
	// Server -> Client
	
	// Write on Server
	public void writeOnContainerSyncServer(NBTTagCompound compound);
	
	// Read on Client
	@OnlyIn(Dist.CLIENT)
	public void readOnContainerSyncClient(NBTTagCompound compound);
	
	// Server -> Client
	
	// Write on Client
	@OnlyIn(Dist.CLIENT)
	public void writeOnContainerSyncClient(NBTTagCompound compound);
	
	// Read on Server
	public void readOnContainerSyncServer(NBTTagCompound compound);
	
	// First data send from server to gui
	public default void writeOnGuiOpenServer(NBTTagCompound compound) {
		writeOnContainerSyncServer(compound);
	}
	
	public default void syncServerToClient(EntityPlayerMP player, BlockPos pos) {
		NBTTagCompound compound = new NBTTagCompound();
		writeOnContainerSyncServer(compound);
		sendMessageToClient(player, pos, compound);
	}
	
	@OnlyIn(Dist.CLIENT)
	public default void syncClientToServer(BlockPos pos) {
		NBTTagCompound compound = new NBTTagCompound();
		writeOnContainerSyncClient(compound);
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
