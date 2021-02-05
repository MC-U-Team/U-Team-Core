package info.u_team.u_team_core.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.api.gui.IRenderTickable;
import info.u_team.u_team_core.container.UContainer;
import info.u_team.u_team_core.util.GuiUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class UContainerScreen<T extends Container> extends FluidContainerScreen<T> {
	
	protected ResourceLocation background;
	protected int backgroundWidth, backgroundHeight;
	
	protected boolean drawTitleText;
	protected boolean drawInventoryText;
	
	public UContainerScreen(T container, PlayerInventory playerInventory, ITextComponent title, ResourceLocation background) {
		super(container, playerInventory, title);
		this.background = background;
		setBackgroundDimensions(256);
		setDrawText(true, true);
	}
	
	public void setBackground(ResourceLocation background) {
		this.background = background;
	}
	
	protected void setBackgroundDimensions(int size) {
		setBackgroundDimensions(size, size);
	}
	
	protected void setBackgroundDimensions(int backgroundWidth, int backgroundHeight) {
		this.backgroundWidth = backgroundWidth;
		this.backgroundHeight = backgroundHeight;
	}
	
	protected void setDrawText(boolean drawTitleText, boolean drawInventoryText) {
		this.drawTitleText = drawTitleText;
		this.drawInventoryText = drawInventoryText;
	}
	
	protected void setSize(int x, int y) {
		xSize = x;
		ySize = y;
		setTextLocation();
	}
	
	protected void setTextLocation() {
		setTextLocation(8, 6, 8, ySize - 94);
	}
	
	protected void setTextLocation(int xTitle, int yTitle, int xInventory, int yInventory) {
		titleX = xTitle;
		titleY = yTitle;
		playerInventoryTitleX = xInventory;
		playerInventoryTitleY = yInventory;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
		if (drawTitleText) {
			font.func_243248_b(matrixStack, title, titleX, titleY, 4210752);
		}
		if (drawInventoryText) {
			font.func_243248_b(matrixStack, playerInventory.getDisplayName(), playerInventoryTitleX, playerInventoryTitleY, 4210752);
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		GuiUtil.clearColor();
		minecraft.getTextureManager().bindTexture(background);
		final int xStart = (width - xSize) / 2;
		final int yStart = (height - ySize) / 2;
		
		blit(matrixStack, xStart, yStart, 0, 0, xSize, ySize, backgroundWidth, backgroundHeight);
	}
	
	@Override
	public void tick() {
		super.tick();
		children.forEach(listener -> {
			if (listener instanceof IRenderTickable) {
				((IRenderTickable) listener).renderTick();
			}
		});
		if (container instanceof UContainer) {
			((UContainer) container).updateTrackedServerToClient();
		}
	}
}
