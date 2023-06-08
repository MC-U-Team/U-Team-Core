package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.util.FontUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.FormattedCharSequence;

public class FabricFontUtilHandler implements FontUtil.Handler {
	
	@Override
	public int drawString(GuiGraphics guiGraphics, Font font, String text, float x, float y, int color, boolean shadow) {
		if (text == null) {
			return 0;
		} else {
			final int length = font.drawInBatch(text, x, y, color, shadow, guiGraphics.pose().last().pose(), guiGraphics.bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0, font.isBidirectional());
			flushIfUnmanaged(guiGraphics);
			return length;
		}
	}
	
	@Override
	public int drawString(GuiGraphics guiGraphics, Font font, FormattedCharSequence text, float x, float y, int color, boolean shadow) {
		final int length = font.drawInBatch(text, x, y, color, shadow, guiGraphics.pose().last().pose(), guiGraphics.bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
		flushIfUnmanaged(guiGraphics);
		return length;
	}
	
	@SuppressWarnings("deprecation")
	private void flushIfUnmanaged(GuiGraphics guiGraphics) {
		guiGraphics.flushIfUnmanaged();
	}
	
}
