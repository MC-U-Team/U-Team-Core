package info.u_team.u_team_core.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.IPerspectiveRenderable;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class UBasicScreen extends UScreen implements IPerspectiveRenderable {
	
	public UBasicScreen(Component title) {
		super(title);
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
		WidgetUtil.renderTooltips(buttons, matrixStack, minecraft, mouseX, mouseY, partialTicks);
	}
}
