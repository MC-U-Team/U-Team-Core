package info.u_team.u_team_core.api.gui;

import info.u_team.u_team_core.util.RGBA;
import net.minecraft.client.gui.GuiGraphics;

public interface BackgroundColorProvider {
	
	RGBA getCurrentBackgroundColor(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
	
}
