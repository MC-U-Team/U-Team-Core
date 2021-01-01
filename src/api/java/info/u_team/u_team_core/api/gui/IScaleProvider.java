package info.u_team.u_team_core.api.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

public interface IScaleProvider {
	
	float getCurrentScale(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks);
	
}
