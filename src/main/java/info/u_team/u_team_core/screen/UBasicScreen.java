package info.u_team.u_team_core.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.PerspectiveRenderable;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.network.chat.Component;

public class UBasicScreen extends UScreen implements PerspectiveRenderable {
	
	public UBasicScreen(Component title) {
		super(title);
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
	}
}
