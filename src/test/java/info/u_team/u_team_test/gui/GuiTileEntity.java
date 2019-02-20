package info.u_team.u_team_test.gui;

import info.u_team.u_team_core.gui.UGuiContainerTileEntity;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.ContainerTileEntity;
import info.u_team.u_team_test.tileentity.TileEntityTileEntity;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class GuiTileEntity extends UGuiContainerTileEntity {
	
	private TileEntityTileEntity tile;
	
	public GuiTileEntity(InventoryPlayer inventoryPlayer, TileEntityTileEntity tileentity) {
		super(new ContainerTileEntity(inventoryPlayer, tileentity), new ResourceLocation(TestMod.modid, "textures/gui/tileentity.png"));
		this.tile = tileentity;
	}
	
	@Override
	protected void initGui() {
		super.initGui();
		
		addButton(new GuiButton(10, 20, 20, "YO") {
			@Override
			public void onClick(double mouseX, double mouseY) {
				System.out.println("TEST");
				tile.valueClient += 100;
				tile.syncClientToServer(tile.getPos());
			}
		});
		
//		buttons.add(new GuiButtonExt(10, 20, 20, "YO") {
//			
//			@Override
//			public void onClick(double mouseX, double mouseY) {
//				System.out.println("TEST");
//				super.onClick(mouseX, mouseY);
//				tile.valueClient += 100;
//				tile.syncClientToServer(tile.getPos());
//			}
//		});
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRenderer.drawString("" + tile.valueClient, 20, 50, 0xFF0F02);
	}
	
}
