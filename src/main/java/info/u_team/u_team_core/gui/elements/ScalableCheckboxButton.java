package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.api.gui.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.*;

public class ScalableCheckboxButton extends CheckboxButton implements IScaleable, IScaleProvider {
	
	protected float scale;
	
	public ScalableCheckboxButton(int x, int y, int width, int height, ITextComponent text, boolean checked, boolean drawText, float scale) {
		this(x, y, width, height, text, checked, drawText, scale, EMTPY_PRESSABLE);
	}
	
	public ScalableCheckboxButton(int x, int y, int width, int height, ITextComponent text, boolean checked, boolean drawText, float scale, IPressable pessable) {
		this(x, y, width, height, text, checked, drawText, scale, pessable, EMPTY_TOOLTIP);
	}
	
	public ScalableCheckboxButton(int x, int y, int width, int height, ITextComponent text, boolean checked, boolean drawText, float scale, ITooltip tooltip) {
		this(x, y, width, height, text, checked, drawText, scale, EMTPY_PRESSABLE, tooltip);
	}
	
	public ScalableCheckboxButton(int x, int y, int width, int height, ITextComponent text, boolean checked, boolean drawText, float scale, IPressable pessable, ITooltip tooltip) {
		super(x, y, width, height, text, checked, drawText, pessable, tooltip);
		this.scale = scale;
	}
	
	@Override
	public float getScale() {
		return scale;
	}
	
	@Override
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	@Override
	public void renderForeground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		if (drawText) {
			final FontRenderer fontRenderer = minecraft.fontRenderer;
			
			final ITextComponent message = getCurrentText();
			if (message != StringTextComponent.EMPTY) {
				final float currentScale = getCurrentScale(matrixStack, mouseX, mouseY, partialTicks);
				
				final float positionFactor = 1 / currentScale;
				
				final float xStart;
				final float yStart = (y + ((int) (height - 8 * currentScale)) / 2) * positionFactor;
				
				if (leftSideText) {
					xStart = (x - ((fontRenderer.getStringPropertyWidth(message) * currentScale) + 4)) * positionFactor;
				} else {
					xStart = (x + width + 4) * positionFactor;
				}
				
				final int color = getCurrentTextColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB();
				
				matrixStack.push();
				matrixStack.scale(currentScale, currentScale, 0);
				
				if (dropShadow) {
					fontRenderer.func_243246_a(matrixStack, getCurrentText(), xStart, yStart, color);
				} else {
					fontRenderer.func_243248_b(matrixStack, getCurrentText(), xStart, yStart, color);
				}
				
				matrixStack.pop();
			}
		}
	}
	
	@Override
	public float getCurrentScale(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return scale;
	}
}
