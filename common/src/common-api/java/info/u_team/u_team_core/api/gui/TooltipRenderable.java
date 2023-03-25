package info.u_team.u_team_core.api.gui;

import com.mojang.blaze3d.vertex.PoseStack;

public interface TooltipRenderable {
	
	void renderTooltip(PoseStack poseStack, int mouseX, int mouseY, float partialTick);
	
}
