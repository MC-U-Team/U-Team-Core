package info.u_team.u_team_core.gui;

import info.u_team.u_team_core.container.UContainerTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class UGuiContainerTileEntity extends UGuiContainer {
	
	protected final TileEntity tileentity;
	
	protected NBTTagCompound initNBT;
	
	public UGuiContainerTileEntity(UContainerTileEntity container, ResourceLocation background) {
		this(container, background, null);
	}
	
	public UGuiContainerTileEntity(UContainerTileEntity container, ResourceLocation background, NBTTagCompound firstNBT) {
		super(container, background);
		tileentity = container.getTileentity();
		initNBT = firstNBT;
	}
	
	@Override
	protected void initGui() {
		super.initGui();
		initGui(initNBT);
	}
	
	public void initGui(NBTTagCompound compound) {
	}
	
	public void handleServerNBT(NBTTagCompound compound) {
		initNBT = compound;
	}
}
