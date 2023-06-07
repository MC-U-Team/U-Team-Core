package info.u_team.u_team_core.api.gui;

import info.u_team.u_team_core.util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public interface TextSettingsProvider {
	
	default Font getCurrentTextFont() {
		return Minecraft.getInstance().font;
	}
	
	default TextRenderType getCurrentTextRenderType() {
		return TextRenderType.SCROLLING;
	}
	
	RGBA getCurrentTextColor(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
	
	static enum TextRenderType {
		SCROLLING,
		ELLIPSIS;
	}
	
}
