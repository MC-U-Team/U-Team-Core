package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.IBackgroundColorProvider;
import info.u_team.u_team_core.api.gui.IPerspectiveRenderable;
import info.u_team.u_team_core.api.gui.IRenderTickable;
import info.u_team.u_team_core.api.gui.ITextColorProvider;
import info.u_team.u_team_core.util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class UTextField extends EditBox implements IRenderTickable, IPerspectiveRenderable, IBackgroundColorProvider, ITextColorProvider {
	
	protected static final ITooltip EMPTY_TOOLTIP = (textField, matrixStack, mouseX, mouseY) -> {
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
	
	public UTextField(Font fontRenderer, int x, int y, int width, int height, UTextField previousTextField, Component title) {
		this(fontRenderer, x, y, width, height, previousTextField, title, EMPTY_TOOLTIP);
	}
	
	public UTextField(Font fontRenderer, int x, int y, int width, int height, UTextField previousTextField, Component title, ITooltip tooltip) {
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
	public void setTextColorUneditable(int color) {
		super.setTextColorUneditable(color);
		setDisabledTextColor(RGBA.fromARGB(color));
	}
	
	public void setPreviousText(UTextField textField) {
		if (textField != null) {
			value = textField.value;
			maxLength = textField.maxLength;
			displayPos = textField.displayPos;
			cursorPos = textField.cursorPos;
			highlightPos = textField.highlightPos;
		}
	}
	
	@Override
	public void renderTick() {
		tick();
	}
	
	@Override
	public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		final var minecraft = Minecraft.getInstance();
		renderBackground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
		renderForeground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderBackground(PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		if (bordered) {
			fill(matrixStack, x - 1, y - 1, x + width + 1, y + height + 1, getCurrentBackgroundFrameColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB());
			fill(matrixStack, x, y, x + width, y + height, getCurrentBackgroundColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB());
		}
	}
	
	@Override
	public void renderForeground(PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		final var currentTextColor = getCurrentTextColor(matrixStack, mouseX, mouseY, partialTicks);
		
		final var currentText = font.plainSubstrByWidth(value.substring(displayPos), getInnerWidth());
		
		final var cursorOffset = cursorPos - displayPos;
		final var selectionOffset = Math.min(highlightPos - displayPos, currentText.length());
		
		final var isCursorInText = cursorOffset >= 0 && cursorOffset <= currentText.length();
		final var shouldCursorBlink = isFocused() && frame / 6 % 2 == 0 && isCursorInText;
		final var isCursorInTheMiddle = cursorPos < value.length() || value.length() >= maxLength;
		
		final var xOffset = bordered ? x + 4 : x;
		final var yOffset = bordered ? y + (height - 8) / 2 : y;
		
		var leftRenderedTextX = xOffset;
		
		if (!currentText.isEmpty()) {
			final var firstTextPart = isCursorInText ? currentText.substring(0, cursorOffset) : currentText;
			leftRenderedTextX = font.drawShadow(matrixStack, formatter.apply(firstTextPart, displayPos), xOffset, yOffset, currentTextColor.getColorARGB());
		}
		
		var rightRenderedTextX = leftRenderedTextX;
		
		if (!isCursorInText) {
			rightRenderedTextX = cursorOffset > 0 ? xOffset + width : xOffset;
		} else if (isCursorInTheMiddle) {
			rightRenderedTextX = leftRenderedTextX - 1;
			--leftRenderedTextX;
		}
		
		if (!currentText.isEmpty() && isCursorInText && cursorOffset < currentText.length()) {
			font.drawShadow(matrixStack, formatter.apply(currentText.substring(cursorOffset), cursorPos), leftRenderedTextX, yOffset, currentTextColor.getColorARGB());
		}
		
		if (!isCursorInTheMiddle && suggestion != null) {
			font.drawShadow(matrixStack, suggestion, rightRenderedTextX - 1, yOffset, getCurrentSuggestionTextColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB());
		}
		
		if (shouldCursorBlink) {
			if (isCursorInTheMiddle) {
				GuiComponent.fill(matrixStack, rightRenderedTextX, yOffset - 1, rightRenderedTextX + 1, yOffset + 1 + 9, getCurrentCursorColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB());
			} else {
				font.drawShadow(matrixStack, "_", rightRenderedTextX, yOffset, currentTextColor.getColorARGB());
			}
		}
		
		if (selectionOffset != cursorOffset) {
			final var selectedX = xOffset + font.width(currentText.substring(0, selectionOffset));
			renderHighlight(rightRenderedTextX, yOffset - 1, selectedX - 1, yOffset + 1 + 9);
		}
	}
	
	@Override
	public void renderToolTip(PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		renderToolTip(matrixStack, mouseX, mouseY);
	}
	
	@Override
	public void renderToolTip(PoseStack matrixStack, int mouseX, int mouseY) {
		onTooltip.onTooltip(this, matrixStack, mouseX, mouseY);
	}
	
	@Override
	public RGBA getCurrentBackgroundColor(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return backgroundColor;
	}
	
	public RGBA getCurrentBackgroundFrameColor(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return isFocused() ? backgroundFrameColor : unfocusedBackgroundFrameColor;
	}
	
	@Override
	public RGBA getCurrentTextColor(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return isEditable ? textColor : disabledTextColor;
	}
	
	public RGBA getCurrentSuggestionTextColor(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return suggestionTextColor;
	}
	
	public RGBA getCurrentCursorColor(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return cursorColor;
	}
	
	@FunctionalInterface
	public interface ITooltip {
		
		void onTooltip(UTextField textField, PoseStack matrixStack, int mouseX, int mouseY);
	}
	
}
