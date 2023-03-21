package info.u_team.u_team_core.api.gui;

import com.mojang.blaze3d.vertex.PoseStack;

public interface WidgetRenderable extends PerspectiveRenderable {
	
	void renderWidgetTexture(PoseStack poseStack, int mouseX, int mouseY, float partialTicks);
	
}
