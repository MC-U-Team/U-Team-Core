package info.u_team.u_team_core.gui;

import info.u_team.u_team_core.container.UContainerTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class UGuiContainerTileEntity extends UGuiContainer {
	
	protected final TileEntity tileentity;
	
	protected boolean handleNextSync;
	
	public UGuiContainerTileEntity(UContainerTileEntity container, ResourceLocation background) {
		super(container, background);
		tileentity = container.getTileentity();
		handleNextSync = true;
	}
	
	// This might be buggy cause initGui might not have run already
	public void handleServerDataInstant(NBTTagCompound compound) {
	}
	
	public void handleServerData(NBTTagCompound compound) {
		if (handleNextSync) {
			handleNextSync = false;
			handleServerDataOnFirstArrival(compound);
		}
	}
	
	public void handleServerDataOnFirstArrival(NBTTagCompound compound) {
	}
	
}
