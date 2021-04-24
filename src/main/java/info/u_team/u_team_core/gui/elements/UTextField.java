package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.api.gui.*;
import info.u_team.u_team_core.util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.ITextComponent;

public class UTextField extends TextFieldWidget implements IRenderTickable, IPerspectiveRenderable, IBackgroundColorProvider, ITextColorProvider {
	
	protected static ITooltip EMPTY_TOOLTIP = (textField, matrixStack, mouseX, mouseY) -> {
	};
	
	protected static final RGBA BLACK = RGBA.BLACK;
	protected static final RGBA WHITE = RGBA.WHITE;
	
	protected static final RGBA LIGHT_GRAY = new RGBA(0xE0E0E0FF);
	protected static final RGBA LIGHTER_GRAY = new RGBA(0xD0D0D0FF);
	protected static final RGBA GRAY = new RGBA(0xA0A0A0FF);
	protected static final RGBA DARKER_GRAY = new RGBA(0x808080FF);
	protected static final RGBA DARK_GRAY = new RGBA(0x707070FF);
	
	protected ITooltip onTooltip;
	
	protected RGBA backgroundFrameColor;
	protected RGBA unfocusedBackgroundFrameColor;
	protected RGBA backgroundColor;
	
	protected RGBA textColor;
	protected RGBA disabledTextColor;
	protected RGBA suggestionTextColor;
	
	protected RGBA cursorColor;
	
	public UTextField(FontRenderer fontRenderer, int x, int y, int width, int height, UTextField previousTextField, ITextComponent title) {
		this(fontRenderer, x, y, width, height, previousTextField, title, EMPTY_TOOLTIP);
	}
	
	public UTextField(FontRenderer fontRenderer, int x, int y, int width, int height, UTextField previousTextField, ITextComponent title, ITooltip tooltip) {
		super(fontRenderer, x, y, width, height, title);
		setPreviousText(previousTextField);
		onTooltip = tooltip;
		backgroundFrameColor = WHITE;
		unfocusedBackgroundFrameColor = GRAY;
		backgroundColor = BLACK;
		textColor = LIGHT_GRAY;
		disabledTextColor = DARK_GRAY;
		suggestionTextColor = DARKER_GRAY;
		cursorColor = LIGHTER_GRAY;
	}
	
	public void setTooltip(ITooltip tooltip) {
		onTooltip = tooltip;
	}
	
	public RGBA getBackgroundFrameColor() {
		return backgroundFrameColor;
	}
	
	public void setBackgroundFrameColor(RGBA backgroundFrameColor) {
		this.backgroundFrameColor = backgroundFrameColor;
	}
	
	public RGBA getUnfocusedBackgroundFrameColor() {
		return unfocusedBackgroundFrameColor;
	}
	
	public void setUnfocusedBackgroundFrameColor(RGBA unfocusedBackgroundFrameColor) {
		this.unfocusedBackgroundFrameColor = unfocusedBackgroundFrameColor;
	}
	
	public RGBA getBackgroundColor() {
		return backgroundColor;
	}
	
	public void setBackgroundColor(RGBA backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public RGBA getTextColor() {
		return textColor;
	}
	
	public void setTextColor(RGBA textColor) {
		this.textColor = textColor;
	}
	
	public RGBA getDisabledTextColor() {
		return disabledTextColor;
	}
	
	public void setDisabledTextColor(RGBA disabledTextColor) {
		this.disabledTextColor = disabledTextColor;
	}
	
	public RGBA getSuggestionTextColor() {
		return suggestionTextColor;
	}
	
	public void setSuggestionTextColor(RGBA suggestionTextColor) {
		this.suggestionTextColor = suggestionTextColor;
	}
	
	public RGBA getCursorColor() {
		return cursorColor;
	}
	
	public void setCursorColor(RGBA cursorColor) {
		this.cursorColor = cursorColor;
	}
	
	@Override
	public void setTextColor(int color) {
		super.setTextColor(color);
		setTextColor(RGBA.fromARGB(color));
	}
	
	@Override
	public void setDisabledTextColour(int color) {
		super.setDisabledTextColour(color);
		setDisabledTextColor(RGBA.fromARGB(color));
	}
	
	public void setPreviousText(UTextField textField) {
		if (textField != null) {
			text = textField.text;
			maxStringLength = textField.maxStringLength;
			lineScrollOffset = textField.lineScrollOffset;
			cursorPosition = textField.cursorPosition;
			selectionEnd = textField.selectionEnd;
		}
	}
	
	@Override
	public void renderTick() {
		tick();
	}
	
	@Override
	public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		final Minecraft minecraft = Minecraft.getInstance();
		renderBackground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
		renderForeground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderBackground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		if (enableBackgroundDrawing) {
			fill(matrixStack, x - 1, y - 1, x + width + 1, y + height + 1, getCurrentBackgroundFrameColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB());
			fill(matrixStack, x, y, x + width, y + height, getCurrentBackgroundColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB());
		}
	}
	
	@Override
	public void renderForeground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		final RGBA currentTextColor = getCurrentTextColor(matrixStack, mouseX, mouseY, partialTicks);
		
		final String currentText = fontRenderer.trimStringToWidth(text.substring(lineScrollOffset), getAdjustedWidth());
		
		final int cursorOffset = cursorPosition - lineScrollOffset;
		final int selectionOffset = Math.min(selectionEnd - lineScrollOffset, currentText.length());
		
		final boolean isCursorInText = cursorOffset >= 0 && cursorOffset <= currentText.length();
		final boolean shouldCursorBlink = isFocused() && cursorCounter / 6 % 2 == 0 && isCursorInText;
		final boolean isCursorInTheMiddle = cursorPosition < text.length() || text.length() >= maxStringLength;
		
		final int xOffset = enableBackgroundDrawing ? x + 4 : x;
		final int yOffset = enableBackgroundDrawing ? y + (height - 8) / 2 : y;
		
		int leftRenderedTextX = xOffset;
		
		if (!currentText.isEmpty()) {
			final String firstTextPart = isCursorInText ? currentText.substring(0, cursorOffset) : currentText;
			leftRenderedTextX = fontRenderer.drawTextWithShadow(matrixStack, textFormatter.apply(firstTextPart, lineScrollOffset), xOffset, yOffset, currentTextColor.getColorARGB());
		}
		
		int rightRenderedTextX = leftRenderedTextX;
		
		if (!isCursorInText) {
			rightRenderedTextX = cursorOffset > 0 ? xOffset + width : xOffset;
		} else if (isCursorInTheMiddle) {
			rightRenderedTextX = leftRenderedTextX - 1;
			--leftRenderedTextX;
		}
		
		if (!currentText.isEmpty() && isCursorInText && cursorOffset < currentText.length()) {
			fontRenderer.drawTextWithShadow(matrixStack, textFormatter.apply(currentText.substring(cursorOffset), cursorPosition), leftRenderedTextX, yOffset, currentTextColor.getColorARGB());
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
			drawSelectionBox(rightRenderedTextX, yOffset - 1, selectedX - 1, yOffset + 1 + 9);
		}
	}
	
	@Override
	public void renderToolTip(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		renderToolTip(matrixStack, mouseX, mouseY);
	}
	
	@Override
	public void renderToolTip(MatrixStack matrixStack, int mouseX, int mouseY) {
		onTooltip.onTooltip(this, matrixStack, mouseX, mouseY);
	}
	
	@Override
	public RGBA getCurrentBackgroundColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return backgroundColor;
	}
	
	public RGBA getCurrentBackgroundFrameColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return isFocused() ? backgroundFrameColor : unfocusedBackgroundFrameColor;
	}
	
	@Override
	public RGBA getCurrentTextColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return isEnabled ? textColor : disabledTextColor;
	}
	
	public RGBA getCurrentSuggestionTextColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return suggestionTextColor;
	}
	
	public RGBA getCurrentCursorColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return cursorColor;
	}
	
	@FunctionalInterface
	public interface ITooltip {
		
		void onTooltip(UTextField textField, MatrixStack matrixStack, int mouseX, int mouseY);
	}
	
}
