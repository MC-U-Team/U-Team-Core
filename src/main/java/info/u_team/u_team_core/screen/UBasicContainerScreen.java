package info.u_team.u_team_core.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.IPerspectiveRenderable;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class UBasicContainerScreen<T extends AbstractContainerMenu> extends UContainerScreen<T> implements IPerspectiveRenderable {
	
	public UBasicContainerScreen(T container, Inventory playerInventory, Component title, ResourceLocation background) {
		super(container, playerInventory, title, background);
	}
	
	public UBasicContainerScreen(T container, Inventory playerInventory, Component title, ResourceLocation background, int xSize, int ySize) {
		super(container, playerInventory, title, background);
		setSize(xSize, ySize);
	}
	
	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		renderForeground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
		renderToolTip(matrixStack, minecraft, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderBackground(PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		renderBackground(matrixStack);
	}
	
	@Override
	public void renderForeground(PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
	}
	
	@Override
	public void renderToolTip(PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		WidgetUtil.renderTooltips(renderables, matrixStack, minecraft, mouseX, mouseY, partialTicks);
		renderTooltip(matrixStack, mouseX, mouseY);
	}
}
