package info.u_team.u_team_core.container;

import info.u_team.u_team_core.api.ISyncedContainerTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class UContainerTileEntity extends UContainer {
	
	protected final TileEntity tileentity;
	
	private CompoundNBT lastCompound;
	
	public UContainerTileEntity(ContainerType<?> type, int id, TileEntity tileentity) {
		super(type, id);
		this.tileentity = tileentity;
	}
	
	public TileEntity getTileentity() {
		return tileentity;
	}
	
	// Sync
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		if (listener instanceof ServerPlayerEntity && tileentity instanceof ISyncedContainerTileEntity) {
			ISyncedContainerTileEntity synced = (ISyncedContainerTileEntity) tileentity;
			if (lastCompound == null) {
				lastCompound = new CompoundNBT();
				synced.writeOnContainerSyncServer(lastCompound);
			}
			synced.sendMessageToClient((ServerPlayerEntity) listener, tileentity.getPos(), lastCompound);
		}
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (tileentity instanceof ISyncedContainerTileEntity) {
			ISyncedContainerTileEntity synced = (ISyncedContainerTileEntity) tileentity;
			
			CompoundNBT compound = new CompoundNBT();
			synced.writeOnContainerSyncServer(compound);
			if (compound.equals(lastCompound)) {
				return;
			}
			lastCompound = compound;
			listeners.stream().filter(listener -> listener instanceof ServerPlayerEntity).map(listener -> (ServerPlayerEntity) listener).forEach(player -> {
				synced.sendMessageToClient(player, tileentity.getPos(), compound);
			});
		}
	}
	
}
