package info.u_team.u_team_core.util;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class FontUtil {
	
	private static final Handler HANDLER = ServiceUtil.loadOne(Handler.class);
	
	public static int drawString(GuiGraphics guiGraphics, Font font, String text, float x, float y, int color, boolean shadow) {
		return HANDLER.drawString(guiGraphics, font, text, x, y, color, shadow);
	}
	
	public static int drawString(GuiGraphics guiGraphics, Font font, Component text, float x, float y, int color, boolean shadow) {
		return drawString(guiGraphics, font, text.getVisualOrderText(), x, y, color, shadow);
	}
	
	public static int drawString(GuiGraphics guiGraphics, Font font, FormattedCharSequence text, float x, float y, int color, boolean shadow) {
		return HANDLER.drawString(guiGraphics, font, text, x, y, color, shadow);
	}
	
	public static interface Handler {
		
		int drawString(GuiGraphics guiGraphics, Font font, String text, float x, float y, int color, boolean shadow);
		
		int drawString(GuiGraphics guiGraphics, Font font, FormattedCharSequence text, float x, float y, int color, boolean shadow);
	}
	
}
