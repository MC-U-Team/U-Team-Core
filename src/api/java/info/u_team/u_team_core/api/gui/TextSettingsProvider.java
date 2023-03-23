package info.u_team.u_team_core.api.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

public interface TextSettingsProvider {
	
	default Font getCurrentTextFont() {
		return Minecraft.getInstance().font;
	}
	
	default TextRenderType getCurrentTextRenderType() {
		return TextRenderType.SCROLLING;
	}
	
	RGBA getCurrentTextColor(PoseStack poseStack, int mouseX, int mouseY, float partialTick);
	
	static enum TextRenderType {
		SCROLLING,
		ELLIPSIS;
	}
	
}
