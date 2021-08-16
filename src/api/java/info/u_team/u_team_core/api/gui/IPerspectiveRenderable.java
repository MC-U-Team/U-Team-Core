package info.u_team.u_team_core.api.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Widget;

public interface IPerspectiveRenderable extends Widget {

	@Override
	void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks);

	void renderBackground(PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks);

	void renderForeground(PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks);

	void renderToolTip(PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks);

}
