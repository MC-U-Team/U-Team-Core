package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.Scalable;
import info.u_team.u_team_core.api.gui.ScaleProvider;
import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class ScalableEditBox extends UEditBox implements Scalable, ScaleProvider {
	
	protected float scale;
	
	public ScalableEditBox(Font font, int x, int y, int width, int height, UEditBox previousEditBox, Component title, float scale) {
		super(font, x, y, width, height, previousEditBox, title);
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
	public void renderForeground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		final float currentScale = getCurrentScale(guiGraphics, mouseX, mouseY, partialTick);
		final float positionFactor = 1 / scale;
		
		final PoseStack poseStack = guiGraphics.pose();
		poseStack.pushPose();
		poseStack.scale(currentScale, currentScale, 0);
		
		final RGBA currentTextColor = WidgetUtil.respectWidgetAlpha(this, getCurrentTextColor(guiGraphics, mouseX, mouseY, partialTick));
		
		final String currentText = font.plainSubstrByWidth(value.substring(displayPos), (int) (getInnerWidth() * positionFactor));
		
		final int cursorOffset = cursorPos - displayPos;
		final int selectionOffset = Math.min(highlightPos - displayPos, currentText.length());
		
		final boolean isCursorInText = cursorOffset >= 0 && cursorOffset <= currentText.length();
		final boolean shouldCursorBlink = isFocused() && frame / 6 % 2 == 0 && isCursorInText;
		final boolean isCursorInTheMiddle = cursorPos < value.length() || value.length() >= maxLength;
		
		final int xOffset = (int) ((bordered ? x + 4 : x) * positionFactor);
		final int yOffset = (int) ((bordered ? y + (height - 8 * currentScale) / 2 : y) * positionFactor);
		
		int leftRenderedTextX = xOffset;
		
		if (!currentText.isEmpty()) {
			final String firstTextPart = isCursorInText ? currentText.substring(0, cursorOffset) : currentText;
			leftRenderedTextX = guiGraphics.drawString(font, formatter.apply(firstTextPart, displayPos), xOffset, yOffset, currentTextColor.getColorARGB());
		}
		
		int rightRenderedTextX = leftRenderedTextX;
		
		if (!isCursorInText) {
			rightRenderedTextX = cursorOffset > 0 ? (int) (xOffset + (width * positionFactor)) : xOffset;
		} else if (isCursorInTheMiddle) {
			rightRenderedTextX = leftRenderedTextX - 1;
			--leftRenderedTextX;
		}
		
		if (!currentText.isEmpty() && isCursorInText && cursorOffset < currentText.length()) {
			guiGraphics.drawString(font, formatter.apply(currentText.substring(cursorOffset), cursorPos), leftRenderedTextX, yOffset, currentTextColor.getColorARGB());
		}
		
		if (hint != null && currentText.isEmpty() && !isFocused()) {
			guiGraphics.drawString(font, hint, leftRenderedTextX, yOffset, WidgetUtil.respectWidgetAlpha(this, getCurrentHintTextColor(guiGraphics, mouseX, mouseY, partialTick)).getColorARGB());
		}
		
		if (!isCursorInTheMiddle && suggestion != null) {
			guiGraphics.drawString(font, suggestion, rightRenderedTextX - 1, yOffset, WidgetUtil.respectWidgetAlpha(this, getCurrentSuggestionTextColor(guiGraphics, mouseX, mouseY, partialTick)).getColorARGB());
		}
		
		if (shouldCursorBlink) {
			if (isCursorInTheMiddle) {
				guiGraphics.fill(rightRenderedTextX, yOffset - 1, rightRenderedTextX + 1, yOffset + 1 + 9, WidgetUtil.respectWidgetAlpha(this, getCurrentCursorColor(guiGraphics, mouseX, mouseY, partialTick)).getColorARGB());
			} else {
				guiGraphics.drawString(font, "_", rightRenderedTextX, yOffset, currentTextColor.getColorARGB());
			}
		}
		
		poseStack.popPose();
		
		if (selectionOffset != cursorOffset) {
			final int selectedX = xOffset + font.width(currentText.substring(0, selectionOffset));
			renderHighlight(guiGraphics, (int) (rightRenderedTextX * currentScale), (int) ((yOffset - 1) * currentScale), (int) ((selectedX - 1) * currentScale), (int) ((yOffset + 1 + 9) * currentScale));
		}
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (active && visible) {
			if (isValidClickButton(button)) {
				final boolean clicked = clicked(mouseX, mouseY);
				
				if (canLoseFocus) {
					setFocused(clicked);
				}
				
				if (isFocused() && clicked) {
					int clickOffset = Mth.floor(mouseX) - x;
					if (bordered) {
						clickOffset -= 4;
					}
					
					clickOffset /= getCurrentScale();
					
					final String currentText = font.plainSubstrByWidth(value.substring(displayPos), (int) (getInnerWidth() * 1 / getCurrentScale()));
					moveCursorTo(font.plainSubstrByWidth(currentText, clickOffset).length() + displayPos);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void setHighlightPos(int position) {
		final int valueLength = value.length();
		
		highlightPos = Mth.clamp(position, 0, valueLength);
		
		if (font != null) {
			if (displayPos > valueLength) {
				displayPos = valueLength;
			}
			
			final int scaledInnerWidth = (int) (getInnerWidth() * 1 / getCurrentScale());
			
			final String currentText = font.plainSubstrByWidth(value.substring(displayPos), scaledInnerWidth);
			final int offset = currentText.length() + displayPos;
			if (highlightPos == displayPos) {
				displayPos -= font.plainSubstrByWidth(value, scaledInnerWidth, true).length();
			}
			
			if (highlightPos > offset) {
				displayPos += highlightPos - offset;
			} else if (highlightPos <= displayPos) {
				displayPos -= displayPos - highlightPos;
			}
			
			displayPos = Mth.clamp(displayPos, 0, valueLength);
		}
	}
	
	@Override
	public float getCurrentScale(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		return getCurrentScale();
	}
	
	public float getCurrentScale() {
		return scale;
	}
}
