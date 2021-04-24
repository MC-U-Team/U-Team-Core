package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.api.gui.*;
import info.u_team.u_team_core.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

/**
 * A button that fixes vanilla not drawing the continuous border if the button is smaller than 20. Also adds utility
 * methods to add an IPressable and ITooltip
 * 
 * @author HyCraftHD
 */
public class UButton extends Button implements IPerspectiveRenderable, IBackgroundColorProvider, ITextProvider {
	
	protected static IPressable EMTPY_PRESSABLE = button -> {
	};
	
	protected static ITooltip EMPTY_TOOLTIP = Button.EMPTY_TOOLTIP;
	
	protected static final RGBA WHITE = RGBA.WHITE;
	protected static final RGBA LIGHT_GRAY = new RGBA(0xA0A0A0FF);
	
	protected ITextureProvider buttonTextureProvider;
	
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
		onTooltip = tooltip;
		buttonTextureProvider = new WidgetTextureProvider(this, this::getYImage);
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
	public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		WidgetUtil.renderButtonLikeWidget(this, buttonTextureProvider, matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderBackground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		renderBg(matrixStack, minecraft, mouseX, mouseY);
	}
	
	@Override
	public void renderForeground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		WidgetUtil.renderText(this, matrixStack, minecraft, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderToolTip(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		renderToolTip(matrixStack, mouseX, mouseY);
	}
	
	@Override
	public RGBA getCurrentBackgroundColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return buttonColor;
	}
	
	@Override
	public ITextComponent getCurrentText() {
		return getMessage();
	}
	
	@Override
	public RGBA getCurrentTextColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return active ? textColor : disabledTextColor;
	}
	
}
