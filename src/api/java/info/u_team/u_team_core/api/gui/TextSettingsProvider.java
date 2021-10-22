package info.u_team.u_team_core.api.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

public interface TextSettingsProvider {
	
	default Font getCurrentTextFont() {
		return Minecraft.getInstance().font;
	}
	
	RGBA getCurrentTextColor(PoseStack poseStack, int mouseX, int mouseY, float partialTicks);
	
}
