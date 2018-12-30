package test;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiTest extends GuiContainer {
	
	private TileEntityTest tile;
	
	public GuiTest(InventoryPlayer playerinv, TileEntityTest tile) {
		super(new ContainerTest(playerinv, tile));
		this.tile = tile;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiButton(0, 60, 30, "Increase"));
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
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/furnace.png"));
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}
	
}
