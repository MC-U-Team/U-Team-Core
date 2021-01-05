package info.u_team.u_team_core.api.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IRenderable;

public interface IPerspectiveRenderable extends IRenderable {
	
	@Override
	void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks);
	
	void renderBackground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks);
	
	void renderForeground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks);
	
	void renderToolTip(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks);
	
}
