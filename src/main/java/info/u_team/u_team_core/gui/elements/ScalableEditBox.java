package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.Scalable;
import info.u_team.u_team_core.api.gui.ScaleProvider;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class ScalableEditBox extends UEditBox implements Scalable, ScaleProvider {
	
	protected float scale;
	
	public ScalableEditBox(Font font, int x, int y, int width, int height, UEditBox previousEditBox, Component title, float scale) {
		this(font, x, y, width, height, previousEditBox, title, scale, EMPTY_TOOLTIP);
	}
	
	public ScalableEditBox(Font font, int x, int y, int width, int height, UEditBox previousEditBox, Component title, float scale, OnTooltip tooltip) {
		super(font, x, y, width, height, previousEditBox, title, tooltip);
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
	public void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		final var currentScale = getCurrentScale(poseStack, mouseX, mouseY, partialTicks);
		
		final var positionFactor = 1 / scale;
		
		poseStack.pushPose();
		poseStack.scale(currentScale, currentScale, 0);
		
		final var currentTextColor = getCurrentTextColor(poseStack, mouseX, mouseY, partialTicks);
		
		final var currentText = font.plainSubstrByWidth(value.substring(displayPos), getInnerWidth());
		
		final var cursorOffset = cursorPos - displayPos;
		final var selectionOffset = Math.min(highlightPos - displayPos, currentText.length());
		
		final var isCursorInText = cursorOffset >= 0 && cursorOffset <= currentText.length();
		final var shouldCursorBlink = isFocused() && frame / 6 % 2 == 0 && isCursorInText;
		final var isCursorInTheMiddle = cursorPos < value.length() || value.length() >= maxLength;
		
		final var xOffset = (int) ((bordered ? x + 4 : x) * positionFactor);
		final var yOffset = (int) ((bordered ? y + (int) (height - 8 * scale) / 2 : y) * positionFactor);
		
		var leftRenderedTextX = xOffset;
		
		if (!currentText.isEmpty()) {
			final var firstTextPart = isCursorInText ? currentText.substring(0, cursorOffset) : currentText;
			leftRenderedTextX = font.drawShadow(poseStack, formatter.apply(firstTextPart, displayPos), xOffset, yOffset, currentTextColor.getColorARGB());
		}
		
		var rightRenderedTextX = leftRenderedTextX;
		
		if (!isCursorInText) {
			rightRenderedTextX = cursorOffset > 0 ? xOffset + width : xOffset;
		} else if (isCursorInTheMiddle) {
			rightRenderedTextX = leftRenderedTextX - 1;
			--leftRenderedTextX;
		}
		
		if (!currentText.isEmpty() && isCursorInText && cursorOffset < currentText.length()) {
			font.drawShadow(poseStack, formatter.apply(currentText.substring(cursorOffset), cursorPos), leftRenderedTextX, yOffset, currentTextColor.getColorARGB());
		}
		
		if (!isCursorInTheMiddle && suggestion != null) {
			font.drawShadow(poseStack, suggestion, rightRenderedTextX - 1, yOffset, getCurrentSuggestionTextColor(poseStack, mouseX, mouseY, partialTicks).getColorARGB());
		}
		
		if (shouldCursorBlink) {
			if (isCursorInTheMiddle) {
				GuiComponent.fill(poseStack, rightRenderedTextX, yOffset - 1, rightRenderedTextX + 1, yOffset + 1 + 9, getCurrentCursorColor(poseStack, mouseX, mouseY, partialTicks).getColorARGB());
			} else {
				font.drawShadow(poseStack, "_", rightRenderedTextX, yOffset, currentTextColor.getColorARGB());
			}
		}
		
		if (selectionOffset != cursorOffset) {
			final var selectedX = xOffset + font.width(currentText.substring(0, selectionOffset));
			renderHighlight((int) (rightRenderedTextX * currentScale), (int) ((yOffset - 1) * currentScale), (int) ((selectedX - 1) * currentScale), (int) ((yOffset + 1 + 9) * currentScale));
		}
		
		poseStack.popPose();
	}
	
	@Override
	public float getCurrentScale(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		return getCurrentScale(mouseX, mouseY);
	}
	
	public float getCurrentScale(double mouseX, double mouseY) {
		return scale;
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (!visible) {
			return false;
		} else {
			final var clicked = clicked(mouseX, mouseY);
			
			if (canLoseFocus) {
				setFocus(clicked);
			}
			
			if (isFocused() && clicked && button == 0) {
				var clickOffset = Mth.floor(mouseX) - x;
				if (bordered) {
					clickOffset -= 4;
				}
				
				clickOffset /= getCurrentScale(mouseX, mouseY);
				
				final var currentText = font.plainSubstrByWidth(value.substring(displayPos), getInnerWidth());
				moveCursorTo(font.plainSubstrByWidth(currentText, clickOffset).length() + displayPos);
				return true;
			} else {
				return false;
			}
		}
	}
	
}
