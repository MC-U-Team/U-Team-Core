package info.u_team.u_team_core.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.IRenderTickable;
import info.u_team.u_team_core.menu.UContainer;
import info.u_team.u_team_core.util.GuiUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class UContainerScreen<T extends AbstractContainerMenu> extends FluidContainerScreen<T> {
	
	protected ResourceLocation background;
	protected int backgroundWidth, backgroundHeight;
	
	protected boolean drawTitleText;
	protected boolean drawInventoryText;
	
	public UContainerScreen(T container, Inventory playerInventory, Component title, ResourceLocation background) {
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
		imageWidth = x;
		imageHeight = y;
		setTextLocation();
	}
	
	protected void setTextLocation() {
		setTextLocation(8, 6, 8, imageHeight - 94);
	}
	
	protected void setTextLocation(int xTitle, int yTitle, int xInventory, int yInventory) {
		titleLabelX = xTitle;
		titleLabelY = yTitle;
		inventoryLabelX = xInventory;
		inventoryLabelY = yInventory;
	}
	
	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		super.renderLabels(matrixStack, mouseX, mouseY);
		if (drawTitleText) {
			font.draw(matrixStack, title, titleLabelX, titleLabelY, 4210752);
		}
		if (drawInventoryText) {
			font.draw(matrixStack, playerInventoryTitle, inventoryLabelX, inventoryLabelY, 4210752);
		}
	}
	
	@Override
	protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		GuiUtil.clearColor();
		minecraft.getTextureManager().bindForSetup(background);
		final var xStart = (width - imageWidth) / 2;
		final var yStart = (height - imageHeight) / 2;
		
		blit(matrixStack, xStart, yStart, 0, 0, imageWidth, imageHeight, backgroundWidth, backgroundHeight);
	}
	
	@Override
	public void containerTick() {
		children().forEach(listener -> { // TODO change to direct variable?
			if (listener instanceof IRenderTickable) {
				((IRenderTickable) listener).renderTick();
			}
		});
		if (menu instanceof UContainer) {
			((UContainer) menu).updateTrackedServerToClient();
		}
	}
}
