package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.api.gui.IScaleable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;

public class ScalableButton extends UButton implements IScaleable {
	
	protected float scale;
	
	public ScalableButton(int x, int y, int width, int height, ITextComponent text, float scale) {
		this(x, y, width, height, text, scale, EMTPY_PRESSABLE);
	}
	
	public ScalableButton(int x, int y, int width, int height, ITextComponent text, float scale, IPressable pessable) {
		this(x, y, width, height, text, scale, pessable, EMPTY_TOOLTIP);
	}
	
	public ScalableButton(int x, int y, int width, int height, ITextComponent text, float scale, ITooltip tooltip) {
		this(x, y, width, height, text, scale, EMTPY_PRESSABLE, tooltip);
	}
	
	public ScalableButton(int x, int y, int width, int height, ITextComponent text, float scale, IPressable pessable, ITooltip tooltip) {
		super(x, y, width, height, text, pessable, tooltip);
		this.scale = scale;
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	@Override
	protected void renderForeground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		if (scale == 1) {
			super.renderForeground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
		} else {
			final FontRenderer fontRenderer = minecraft.fontRenderer;
			
			ITextComponent message = getMessage();
			
			if (message != StringTextComponent.EMPTY) {
				final int messageWidth = MathHelper.ceil(scale * fontRenderer.getStringPropertyWidth(message));
				final int ellipsisWidth = MathHelper.ceil(scale * fontRenderer.getStringWidth("..."));
				
				if (messageWidth > width - 6 && messageWidth > ellipsisWidth) {
					message = new StringTextComponent(fontRenderer.func_238417_a_(message, width - 6 - ellipsisWidth).getString() + "...");
				}
				
				final float positionFactor = 1 / scale;
				
				final float xStart = (x + (width / 2) - messageWidth / 2) * positionFactor;
				final float yStart = (y + ((int) (height - 8 * scale)) / 2) * positionFactor;
				
				matrixStack.push();
				matrixStack.scale(scale, scale, 0);
				fontRenderer.func_243246_a(matrixStack, message, xStart, yStart, getFGColor());
				matrixStack.pop();
			}
		}
	}
}
