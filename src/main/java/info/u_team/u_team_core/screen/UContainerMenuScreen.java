package info.u_team.u_team_core.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.PerspectiveRenderable;
import info.u_team.u_team_core.api.gui.RenderTickable;
import info.u_team.u_team_core.api.gui.TooltipRenderable;
import info.u_team.u_team_core.menu.UContainerMenu;
import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class UContainerMenuScreen<T extends AbstractContainerMenu> extends FluidContainerMenuScreen<T> implements PerspectiveRenderable, TooltipRenderable {
	
	protected static final RGBA DEFAULT_TEXT_COLOR = new RGBA(0x404040FF);
	
	protected ResourceLocation background;
	protected int backgroundWidth, backgroundHeight;
	protected RGBA backgroundColor;
	
	protected boolean drawTitleText;
	protected boolean drawInventoryText;
	protected RGBA textColor;
	
	public UContainerMenuScreen(T menu, Inventory playerInventory, Component title, ResourceLocation background, int imageWidth, int imageHeight) {
		this(menu, playerInventory, title, background);
		setImageDimensions(imageWidth, imageHeight);
	}
	
	public UContainerMenuScreen(T menu, Inventory playerInventory, Component title, ResourceLocation background) {
		super(menu, playerInventory, title);
		this.background = background;
		backgroundWidth = 256;
		backgroundHeight = 256;
		backgroundColor = RGBA.WHITE;
		drawTitleText = true;
		drawInventoryText = true;
		textColor = DEFAULT_TEXT_COLOR;
	}
	
	protected void setBackground(ResourceLocation background) {
		this.background = background;
	}
	
	protected void setBackgroundDimensions(int size) {
		setBackgroundDimensions(size, size);
	}
	
	protected void setBackgroundDimensions(int backgroundWidth, int backgroundHeight) {
		this.backgroundWidth = backgroundWidth;
		this.backgroundHeight = backgroundHeight;
	}
	
	protected void setImageDimensions(int imageWidth, int imageHeight) {
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		setDefaultTextLocation();
	}
	
	protected void setDrawText(boolean drawTitleText, boolean drawInventoryText) {
		this.drawTitleText = drawTitleText;
		this.drawInventoryText = drawInventoryText;
	}
	
	protected void setDefaultTextLocation() {
		setTextLocation(8, 6, 8, imageHeight - 94);
	}
	
	protected void setTextLocation(int titleLabelX, int titleLabelY, int inventoryLabelX, int inventoryLabelY) {
		this.titleLabelX = titleLabelX;
		this.titleLabelY = titleLabelY;
		this.inventoryLabelX = inventoryLabelX;
		this.inventoryLabelY = inventoryLabelY;
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(poseStack, mouseX, mouseY, partialTicks);
		super.render(poseStack, mouseX, mouseY, partialTicks);
		renderForeground(poseStack, mouseX, mouseY, partialTicks);
		renderTooltip(poseStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderBackground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(poseStack);
	}
	
	@Override
	public void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
	}
	
	@Override
	public void renderTooltip(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		renderTooltip(poseStack, mouseX, mouseY);
	}
	
	@Override
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		super.renderLabels(poseStack, mouseX, mouseY);
		if (drawTitleText) {
			font.draw(poseStack, title, titleLabelX, titleLabelY, textColor.getColorARGB());
		}
		if (drawInventoryText) {
			font.draw(poseStack, playerInventoryTitle, inventoryLabelX, inventoryLabelY, textColor.getColorARGB());
		}
	}
	
	@Override
	protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
		RenderUtil.drawTexturedQuad(poseStack, leftPos, topPos, imageWidth, imageHeight, imageWidth, imageHeight, 0, 0, backgroundWidth, backgroundHeight, 0, background, backgroundColor);
	}
	
	@Override
	public void containerTick() {
		for (final GuiEventListener listener : children()) {
			if (listener instanceof final RenderTickable tickable) {
				tickable.renderTick();
			}
		}
		if (menu instanceof final UContainerMenu uMenu) {
			uMenu.broadcastChangesToServer();
		}
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		final GuiEventListener focused = getFocused();
		if (focused != null && isDragging() && button == 0) {
			focused.mouseDragged(mouseX, mouseY, button, dragX, dragY);
		}
		return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
	}
}
