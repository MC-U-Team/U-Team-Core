package info.u_team.u_team_core.container;

import info.u_team.u_team_core.api.ISyncedContainerTileEntity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

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
