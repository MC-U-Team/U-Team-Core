package info.u_team.u_team_core.api.gui;

import net.minecraft.client.gui.GuiGraphics;

public interface WidgetRenderable extends PerspectiveRenderable {
	
	void renderWidgetTexture(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
	
}
