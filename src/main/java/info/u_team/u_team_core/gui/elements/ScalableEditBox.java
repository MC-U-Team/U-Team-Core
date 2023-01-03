package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.Scalable;
import info.u_team.u_team_core.api.gui.ScaleProvider;
import info.u_team.u_team_core.util.RGBA;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
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
	public void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		final float currentScale = getCurrentScale(poseStack, mouseX, mouseY, partialTicks);
		
		final float positionFactor = 1 / scale;
		
		poseStack.pushPose();
		poseStack.scale(currentScale, currentScale, 0);
		
		final RGBA currentTextColor = getCurrentTextColor(poseStack, mouseX, mouseY, partialTicks);
		
		final String currentText = font.plainSubstrByWidth(value.substring(displayPos), getInnerWidth());
		
		final int cursorOffset = cursorPos - displayPos;
		final int selectionOffset = Math.min(highlightPos - displayPos, currentText.length());
		
		final boolean isCursorInText = cursorOffset >= 0 && cursorOffset <= currentText.length();
		final boolean shouldCursorBlink = isFocused() && frame / 6 % 2 == 0 && isCursorInText;
		final boolean isCursorInTheMiddle = cursorPos < value.length() || value.length() >= maxLength;
		
		final int xOffset = (int) ((bordered ? x + 4 : x) * positionFactor);
		final int yOffset = (int) ((bordered ? y + (int) (height - 8 * scale) / 2 : y) * positionFactor);
		
		int leftRenderedTextX = xOffset;
		
		if (!currentText.isEmpty()) {
			final String firstTextPart = isCursorInText ? currentText.substring(0, cursorOffset) : currentText;
			leftRenderedTextX = font.drawShadow(poseStack, formatter.apply(firstTextPart, displayPos), xOffset, yOffset, currentTextColor.getColorARGB());
		}
		
		int rightRenderedTextX = leftRenderedTextX;
		
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
			final int selectedX = xOffset + font.width(currentText.substring(0, selectionOffset));
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
			final boolean clicked = clicked(mouseX, mouseY);
			
			if (canLoseFocus) {
				setFocus(clicked);
			}
			
			if (isFocused() && clicked && button == 0) {
				int clickOffset = Mth.floor(mouseX) - x;
				if (bordered) {
					clickOffset -= 4;
				}
				
				clickOffset /= getCurrentScale(mouseX, mouseY);
				
				final String currentText = font.plainSubstrByWidth(value.substring(displayPos), getInnerWidth());
				moveCursorTo(font.plainSubstrByWidth(currentText, clickOffset).length() + displayPos);
				return true;
			} else {
				return false;
			}
		}
	}
	
}
