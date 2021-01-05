package info.u_team.u_team_core.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.util.text.ITextComponent;

public class UBasicScreen extends UScreen {
	
	public UBasicScreen(ITextComponent title) {
		super(title);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		buttons.forEach(widget -> widget.renderToolTip(matrixStack, mouseX, mouseY));
	}
}
