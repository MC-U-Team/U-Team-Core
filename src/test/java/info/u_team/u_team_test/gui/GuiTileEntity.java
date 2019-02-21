package info.u_team.u_team_test.gui;

import info.u_team.u_team_core.gui.UGuiContainerTileEntity;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.ContainerTileEntity;
import info.u_team.u_team_test.tileentity.TileEntityTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.client.config.*;

@OnlyIn(Dist.CLIENT)
public class GuiTileEntity extends UGuiContainerTileEntity {
	
	private InventoryPlayer inventoryPlayer;
	
	private TileEntityTileEntity tileentity;
	
	public GuiTileEntity(InventoryPlayer inventoryPlayer, TileEntityTileEntity tileentity, NBTTagCompound firstNBT) {
		super(new ContainerTileEntity(inventoryPlayer, tileentity), new ResourceLocation(TestMod.modid, "textures/gui/tileentity.png"), firstNBT);
		this.inventoryPlayer = inventoryPlayer;
		this.tileentity = tileentity;
		
		xSize = 176;
		ySize = 173;
	}
	
	@Override
	public void initGui(NBTTagCompound compound) {
		addButton(new GuiButtonExt(0, guiLeft + xSize / 2 - 25, guiTop + 3, 50, 15, "Add 100") {
			
			@Override
			public void onClick(double mouseX, double mouseY) {
				super.onClick(mouseX, mouseY);
				
				tileentity.value += 100;
				tileentity.syncClientToServer(tileentity.getPos());
			}
		});
		
		addButton(new GuiSlider(1, guiLeft + 7, guiTop + 19, 161, 20, "Cooldown: ", " Ticks", 0, 100, compound.getInt("cooldown"), false, true) {
			
			@Override
			public void onRelease(double mouseX, double mouseY) {
				super.onRelease(mouseX, mouseY);
				tileentity.cooldown = this.getValueInt();
				tileentity.syncClientToServer(tileentity.getPos());
			}
		});
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRenderer.drawString("" + tileentity.value, xSize / 2 + 32, 6, 4210752);
		fontRenderer.drawString("Tile Entity", 8, 6, 4210752);
		fontRenderer.drawString(inventoryPlayer.getDisplayName().getFormattedText(), 8.0F, ySize - 94, 4210752);
	}
	
}
