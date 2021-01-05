package info.u_team.u_team_core.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.api.gui.IPerspectiveRenderable;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;

public class UBasicScreen extends UScreen implements IPerspectiveRenderable {
	
	public UBasicScreen(ITextComponent title) {
		super(title);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		renderForeground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
		renderToolTip(matrixStack, minecraft, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderBackground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		renderBackground(matrixStack);
	}
	
	@Override
	public void renderForeground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
	}
	
	@Override
	public void renderToolTip(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		WidgetUtil.renderTooltips(buttons, matrixStack, minecraft, mouseX, mouseY, partialTicks);
	}
}
