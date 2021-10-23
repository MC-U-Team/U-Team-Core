package info.u_team.u_team_core.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.PerspectiveRenderable;
import info.u_team.u_team_core.api.gui.RenderTickable;
import info.u_team.u_team_core.menu.UContainerMenu;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class UMenuContainerScreen<T extends AbstractContainerMenu> extends FluidContainerMenuScreen<T> implements PerspectiveRenderable {
	
	protected ResourceLocation background;
	protected int backgroundWidth, backgroundHeight;
	
	protected boolean drawTitleText;
	protected boolean drawInventoryText;
	
	public UMenuContainerScreen(T menu, Inventory playerInventory, Component title, ResourceLocation background, int xSize, int ySize) {
		this(menu, playerInventory, title, background);
		setSize(xSize, ySize);
	}
	
	public UMenuContainerScreen(T menu, Inventory playerInventory, Component title, ResourceLocation background) {
		super(menu, playerInventory, title);
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
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(poseStack, mouseX, mouseY, partialTicks);
		super.render(poseStack, mouseX, mouseY, partialTicks);
		renderForeground(poseStack, mouseX, mouseY, partialTicks);
		renderToolTip(poseStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderBackground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(poseStack);
	}
	
	@Override
	public void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
	}
	
	@Override
	public void renderToolTip(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		WidgetUtil.renderTooltips(renderables, poseStack, mouseX, mouseY, partialTicks);
		renderTooltip(poseStack, mouseX, mouseY);
	}
	
	@Override
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		super.renderLabels(poseStack, mouseX, mouseY);
		if (drawTitleText) {
			font.draw(poseStack, title, titleLabelX, titleLabelY, 0x404040);
		}
		if (drawInventoryText) {
			font.draw(poseStack, playerInventoryTitle, inventoryLabelX, inventoryLabelY, 0x404040);
		}
	}
	
	@Override
	protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShaderTexture(0, background);
		
		blit(poseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight, backgroundWidth, backgroundHeight);
	}
	
	@Override
	public void containerTick() {
		for (final var listener : children()) {
			if (listener instanceof RenderTickable tickable) {
				tickable.renderTick();
			}
		}
		if (menu instanceof UContainerMenu uMenu) {
			uMenu.broadcastChangesToServer();
		}
	}
}
