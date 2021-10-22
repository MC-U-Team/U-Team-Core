package info.u_team.u_team_core.api.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.Widget;

public interface PerspectiveRenderable extends Widget {
	
	@Override
	void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks);
	
	void renderBackground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks);
	
	void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks);
	
	void renderToolTip(PoseStack poseStack, int mouseX, int mouseY, float partialTicks);
	
}
