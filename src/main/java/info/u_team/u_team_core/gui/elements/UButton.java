package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.*;

/**
 * A button that fixes vanilla not drawing the continuous border if the button is smaller than 20. Also adds utility
 * methods to add an {@link IPressable} and {@link ITooltip}
 * 
 * @author HyCraftHD
 */
public class UButton extends Button {
	
	protected static IPressable EMTPY_PRESSABLE = button -> {
	};
	
	protected static ITooltip EMPTY_TOOLTIP = field_238486_s_;
	
	protected static final RGBA WHITE = RGBA.WHITE;
	protected static final RGBA LIGHT_GRAY = new RGBA(0xA0A0A0FF);
	
	protected RGBA buttonColor;
	
	protected RGBA textColor;
	protected RGBA disabledTextColor;
	
	public UButton(int x, int y, int width, int height, ITextComponent text) {
		this(x, y, width, height, text, EMTPY_PRESSABLE);
	}
	
	public UButton(int x, int y, int width, int height, ITextComponent text, IPressable pessable) {
		this(x, y, width, height, text, pessable, EMPTY_TOOLTIP);
	}
	
	public UButton(int x, int y, int width, int height, ITextComponent text, ITooltip tooltip) {
		this(x, y, width, height, text, EMTPY_PRESSABLE, tooltip);
	}
	
	public UButton(int x, int y, int width, int height, ITextComponent text, IPressable pessable, ITooltip tooltip) {
		super(x, y, width, height, text, pessable);
		buttonColor = WHITE;
		textColor = WHITE;
		disabledTextColor = LIGHT_GRAY;
	}
	
	public void setPressable(IPressable pressable) {
		onPress = pressable;
	}
	
	public void setPressable(Runnable runnable) {
		onPress = button -> runnable.run();
	}
	
	public void setTooltip(ITooltip tooltip) {
		onTooltip = tooltip;
	}
	
	public RGBA getButtonColor() {
		return buttonColor;
	}
	
	public void setButtonColor(RGBA buttonColor) {
		this.buttonColor = buttonColor;
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
	
	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		RenderUtil.enableBlend();
		RenderUtil.defaultBlendFunc();
		GuiUtil.drawContinuousTexturedBox(matrixStack, WIDGETS_LOCATION, x, y, 0, 46 + getYImage(isHovered()) * 20, width, height, 200, 20, 2, 3, 2, 2, 0, getCurrentButtonColor(matrixStack, mouseY, mouseY, partialTicks));
		RenderUtil.disableBlend();
		
		final Minecraft minecraft = Minecraft.getInstance();
		
		renderBackground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
		renderForeground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
	}
	
	protected void renderBackground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		renderBg(matrixStack, minecraft, mouseX, mouseY);
	}
	
	protected void renderForeground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		final FontRenderer fontRenderer = minecraft.fontRenderer;
		
		ITextComponent message = getCurrentMessage();
		if (message != StringTextComponent.EMPTY) {
			final int messageWidth = fontRenderer.getStringPropertyWidth(message);
			final int ellipsisWidth = fontRenderer.getStringWidth("...");
			
			if (messageWidth > width - 6 && messageWidth > ellipsisWidth) {
				message = new StringTextComponent(fontRenderer.func_238417_a_(message, width - 6 - ellipsisWidth).getString() + "...");
			}
			
			final float xStart = (x + (width / 2) - messageWidth / 2);
			final float yStart = (y + ((int) (height - 8)) / 2);
			
			fontRenderer.func_243246_a(matrixStack, message, xStart, yStart, getCurrentTextColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB());
		}
	}
	
	protected ITextComponent getCurrentMessage() {
		return getMessage();
	}
	
	protected RGBA getCurrentButtonColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return buttonColor;
	}
	
	protected RGBA getCurrentTextColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return active ? textColor : disabledTextColor;
	}
}
