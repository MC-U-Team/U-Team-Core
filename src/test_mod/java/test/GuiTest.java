package test;

import java.io.IOException;

import info.u_team.u_team_core.gui.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiSlider;

public class GuiTest extends UGuiContainerTileEntity {
	
	private TileEntityTest tile;
	
	private GuiSlider slider;
	
	public GuiTest(InventoryPlayer playerinv, TileEntityTest tile) {
		super(new ContainerTest(playerinv, tile), new ResourceLocation("textures/gui/container/furnace.png"));
		this.tile = tile;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiButton(0, 60, 30, "Increase"));
		
		buttonList.add(slider = new GuiSlider(1, 20, 20, 200, 20, "test", " t", 0, 10000, 0, false, true, slider -> {
			
		}));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0) {
			tile.clientTestInteger += 100;
			tile.syncClientToServer(tile.getPos());
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		mc.fontRenderer.drawString(tile.clientTestInteger + "", 30, 30, 0xFF0F02);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		// GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		// this.mc.getTextureManager().bindTexture(new
		// ResourceLocation("textures/gui/container/furnace.png"));
		// int i = (this.width - this.xSize) / 2;
		// int j = (this.height - this.ySize) / 2;
		// this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	public void handleServerDataOnFirstArrival(NBTTagCompound compound) {
		int test = compound.getInteger("int");
		System.out.println("SLIDER  " + test);
		slider.setValue(test);
		slider.updateSlider();
	}
	
}
