package info.u_team.u_team_core.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.PerspectiveRenderable;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class UBasicMenuContainerScreen<T extends AbstractContainerMenu> extends UContainerMenuScreen<T> implements PerspectiveRenderable {
	
	public UBasicMenuContainerScreen(T menu, Inventory playerInventory, Component title, ResourceLocation background) {
		super(menu, playerInventory, title, background);
	}
	
	public UBasicMenuContainerScreen(T menu, Inventory playerInventory, Component title, ResourceLocation background, int xSize, int ySize) {
		super(menu, playerInventory, title, background);
		setSize(xSize, ySize);
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
}
