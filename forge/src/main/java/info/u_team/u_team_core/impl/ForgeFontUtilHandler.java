package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.util.FontUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.FormattedCharSequence;

public class ForgeFontUtilHandler implements FontUtil.Handler {
	
	@Override
	public int drawString(GuiGraphics guiGraphics, Font font, String text, float x, float y, int color, boolean shadow) {
		return guiGraphics.drawString(font, text, x, y, color, shadow);
	}
	
	@Override
	public int drawString(GuiGraphics guiGraphics, Font font, FormattedCharSequence text, float x, float y, int color, boolean shadow) {
		return guiGraphics.drawString(font, text, x, y, color, shadow);
	}
	
}
