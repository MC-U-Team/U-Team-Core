package info.u_team.u_team_core.api.gui;

import com.mojang.blaze3d.vertex.PoseStack;

public interface ScaleProvider {
	
	float getCurrentScale(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks);
	
}
