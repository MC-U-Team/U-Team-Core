package info.u_team.u_team_core.api.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.util.RGBA;

public interface ITextColorProvider {
	
	RGBA getCurrentTextColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks);
	
}
