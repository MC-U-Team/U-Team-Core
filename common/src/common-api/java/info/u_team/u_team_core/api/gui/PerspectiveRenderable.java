package info.u_team.u_team_core.api.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;

public interface PerspectiveRenderable extends Renderable {
	
	@Override
	void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
	
	void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
	
	void renderForeground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
	
}
