package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.api.gui.*;
import info.u_team.u_team_core.util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.text.ITextComponent;

public class ScalableTextField extends UTextField implements IScaleable, IScaleProvider {
	
	protected float scale;
	
	public ScalableTextField(FontRenderer fontRenderer, int x, int y, int width, int height, UTextField previousTextField, ITextComponent title, float scale) {
		this(fontRenderer, x, y, width, height, previousTextField, title, scale, EMPTY_TOOLTIP);
	}
	
	public ScalableTextField(FontRenderer fontRenderer, int x, int y, int width, int height, UTextField previousTextField, ITextComponent title, float scale, ITooltip tooltip) {
		super(fontRenderer, x, y, width, height, previousTextField, title, tooltip);
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
		final float currentScale = getCurrentScale(matrixStack, mouseX, mouseY, partialTicks);
		
		final float positionFactor = 1 / scale;
		
		matrixStack.push();
		matrixStack.scale(currentScale, currentScale, 0);
		
		final RGBA currentTextColor = getCurrentTextColor(matrixStack, mouseX, mouseY, partialTicks);
		
		final String currentText = fontRenderer.func_238412_a_(text.substring(lineScrollOffset), getAdjustedWidth());
		
		final int cursorOffset = cursorPosition - lineScrollOffset;
		final int selectionOffset = Math.min(selectionEnd - lineScrollOffset, currentText.length());
		
		final boolean isCursorInText = cursorOffset >= 0 && cursorOffset <= currentText.length();
		final boolean shouldCursorBlink = isFocused() && cursorCounter / 6 % 2 == 0 && isCursorInText;
		final boolean isCursorInTheMiddle = cursorPosition < text.length() || text.length() >= maxStringLength;
		
		final int xOffset = (int) ((enableBackgroundDrawing ? x + 4 : x) * positionFactor);
		final int yOffset = (int) ((enableBackgroundDrawing ? y + (int) (height - 8 * scale) / 2 : y) * positionFactor);
		
		int leftRenderedTextX = xOffset;
		
		if (!currentText.isEmpty()) {
			final String firstTextPart = isCursorInText ? currentText.substring(0, cursorOffset) : currentText;
			leftRenderedTextX = fontRenderer.func_238407_a_(matrixStack, textFormatter.apply(firstTextPart, lineScrollOffset), xOffset, yOffset, currentTextColor.getColorARGB());
		}
		
		int rightRenderedTextX = leftRenderedTextX;
		
		if (!isCursorInText) {
			rightRenderedTextX = cursorOffset > 0 ? xOffset + width : xOffset;
		} else if (isCursorInTheMiddle) {
			rightRenderedTextX = leftRenderedTextX - 1;
			--leftRenderedTextX;
		}
		
		if (!currentText.isEmpty() && isCursorInText && cursorOffset < currentText.length()) {
			fontRenderer.func_238407_a_(matrixStack, textFormatter.apply(currentText.substring(cursorOffset), cursorPosition), leftRenderedTextX, yOffset, currentTextColor.getColorARGB());
		}
		
		if (!isCursorInTheMiddle && suggestion != null) {
			fontRenderer.drawStringWithShadow(matrixStack, suggestion, rightRenderedTextX - 1, yOffset, getCurrentSuggestionTextColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB());
		}
		
		if (shouldCursorBlink) {
			if (isCursorInTheMiddle) {
				AbstractGui.fill(matrixStack, rightRenderedTextX, yOffset - 1, rightRenderedTextX + 1, yOffset + 1 + 9, getCurrentCursorColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB());
			} else {
				fontRenderer.drawStringWithShadow(matrixStack, "_", rightRenderedTextX, yOffset, currentTextColor.getColorARGB());
			}
		}
		
		if (selectionOffset != cursorOffset) {
			final int selectedX = xOffset + fontRenderer.getStringWidth(currentText.substring(0, selectionOffset));
			drawSelectionBox((int) (rightRenderedTextX * currentScale), (int) ((yOffset - 1) * currentScale), (int) ((selectedX - 1) * currentScale), (int) ((yOffset + 1 + 9) * currentScale));
		}
		
		matrixStack.pop();
	}
	
	@Override
	public float getCurrentScale(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return scale;
	}
	
}
