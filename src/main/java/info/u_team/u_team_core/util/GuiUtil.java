package info.u_team.u_team_core.util;

import net.minecraft.client.gui.AbstractGui;
import net.minecraftforge.api.distmarker.*;

/**
 * Some utility methods for guis
 * 
 * @author HyCraftHD
 */
@OnlyIn(Dist.CLIENT)
public class GuiUtil {
	
	/**
	 * Draws the default container border
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void drawContainerBorder(int x, int y, int width, int height) {
		// ARGB ... Strange formal
		
		final int darkColor = 0xFF373737;
		final int mediumColor = 0xFF8B8B8B;
		final int brightColor = 0xFFFFFFFF;
		
		AbstractGui.fill(x, y, x + width - 1, y + 1, darkColor);
		AbstractGui.fill(x, y, x + 1, y + height - 1, darkColor);
		
		AbstractGui.fill(x + width - 1, y, x + width, y + 1, mediumColor);
		AbstractGui.fill(x, y + height - 1, x + 1, y + height, mediumColor);
		
		AbstractGui.fill(x + 1, y + height, x + width - 1, y + height - 1, brightColor);
		AbstractGui.fill(x + width - 1, y + 1, x + width, y + height, brightColor);
	}
	
}
