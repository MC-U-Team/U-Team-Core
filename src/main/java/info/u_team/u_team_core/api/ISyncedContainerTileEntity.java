package info.u_team.u_team_core.api;

import info.u_team.u_team_core.intern.init.UCoreNetwork;
import info.u_team.u_team_core.intern.network.MessageSyncedContainer;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.network.PacketDistributor;

public interface ISyncedContainerTileEntity {
	
	// Server -> Client
	
	// Write on Server
	public void writeOnContainerSyncServer(CompoundNBT compound);
	
	// Read on Client
	@OnlyIn(Dist.CLIENT)
	public void readOnContainerSyncClient(CompoundNBT compound);
	
	// Server -> Client
	
	// Write on Client
	@OnlyIn(Dist.CLIENT)
	public void writeOnContainerSyncClient(CompoundNBT compound);
	
	// Read on Server
	public void readOnContainerSyncServer(CompoundNBT compound);
	
	// First data send from server to gui
	public default void writeOnGuiOpenServer(CompoundNBT compound) {
		writeOnContainerSyncServer(compound);
	}
	
	public default void syncServerToClient(ServerPlayerEntity player, BlockPos pos) {
		CompoundNBT compound = new CompoundNBT();
		writeOnContainerSyncServer(compound);
		sendMessageToClient(player, pos, compound);
	}
	
	@OnlyIn(Dist.CLIENT)
	public default void syncClientToServer(BlockPos pos) {
		CompoundNBT compound = new CompoundNBT();
		writeOnContainerSyncClient(compound);
		sendMessageToServer(pos, compound);
	}
	
	// This method access internal stuff. DO NOT OVERWRITE
	public default void sendMessageToClient(ServerPlayerEntity player, BlockPos pos, CompoundNBT compound) {
		UCoreNetwork.network.send(PacketDistributor.PLAYER.with(() -> player), new MessageSyncedContainer(pos, compound));
	}
	
	// This method access internal stuff. DO NOT OVERWRITE
	@OnlyIn(Dist.CLIENT)
	public default void sendMessageToServer(BlockPos pos, CompoundNBT compound) {
		UCoreNetwork.network.sendToServer(new MessageSyncedContainer(pos, compound));
	}
	
}
