package info.u_team.u_team_core.gui;

import info.u_team.u_team_core.container.UContainerTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class UGuiContainerTileEntity extends UGuiContainer {
	
	protected final TileEntity tileentity;
	
	protected CompoundNBT initNBT;
	
	public UGuiContainerTileEntity(UContainerTileEntity container, PlayerInventory playerInventory, ITextComponent title, ResourceLocation background) {
		this(container, playerInventory, title, background, null);
	}
	
	public UGuiContainerTileEntity(UContainerTileEntity container, PlayerInventory playerInventory, ITextComponent title, ResourceLocation background, CompoundNBT firstNBT) {
		super(container, playerInventory, title, background);
		tileentity = container.getTileentity();
		initNBT = firstNBT;
	}
	
	@Override
	protected void init() {
		super.init();
		initGui(initNBT);
	}
	
	public void initGui(CompoundNBT compound) {
	}
	
	public void handleServerNBT(CompoundNBT compound) {
		initNBT = compound;
	}
}
