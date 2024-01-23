package info.u_team.u_team_core.api.gui;

import net.minecraft.client.gui.GuiGraphics;

public interface ScaleProvider {
	
	float getCurrentScale(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
	
}
