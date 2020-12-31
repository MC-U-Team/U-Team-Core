package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.api.gui.*;
import info.u_team.u_team_core.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.widget.Slider;

public class USlider extends Slider implements IPerspectiveRenderable, IBackgroundColorProvider, ITextProvider {
	
	protected static ISlider EMTPY_SLIDER = slider -> {
	};
	
	protected static ITooltip EMPTY_TOOLTIP = UButton.EMPTY_TOOLTIP;
	
	protected static final RGBA WHITE = UButton.WHITE;
	protected static final RGBA LIGHT_GRAY = UButton.LIGHT_GRAY;
	
	protected RGBA sliderBackgroundColor;
	protected RGBA sliderColor;
	
	protected RGBA textColor;
	protected RGBA disabledTextColor;
	
	public USlider(int x, int y, int width, int height, ITextComponent prefix, ITextComponent suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription) {
		this(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, EMTPY_SLIDER);
	}
	
	public USlider(int x, int y, int width, int height, ITextComponent prefix, ITextComponent suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, ISlider slider) {
		this(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, slider, EMPTY_TOOLTIP);
	}
	
	public USlider(int x, int y, int width, int height, ITextComponent prefix, ITextComponent suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, ITooltip tooltip) {
		this(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, EMTPY_SLIDER, tooltip);
	}
	
	public USlider(int x, int y, int width, int height, ITextComponent prefix, ITextComponent suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, ISlider slider, ITooltip tooltip) {
		super(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, UButton.EMTPY_PRESSABLE, slider);
		onTooltip = tooltip;
		sliderBackgroundColor = WHITE;
		sliderColor = WHITE;
		textColor = WHITE;
		disabledTextColor = LIGHT_GRAY;
	}
	
	public void setSlider(ISlider slider) {
		parent = slider;
	}
	
	public void setSlider(Runnable runnable) {
		parent = slider -> runnable.run();
	}
	
	public void setTooltip(ITooltip tooltip) {
		onTooltip = tooltip;
	}
	
	public RGBA getSliderBackgroundColor() {
		return sliderBackgroundColor;
	}
	
	public void setSliderBackgroundColor(RGBA sliderBackgroundColor) {
		this.sliderBackgroundColor = sliderBackgroundColor;
	}
	
	public RGBA getSliderColor() {
		return sliderColor;
	}
	
	public void setSliderColor(RGBA sliderColor) {
		this.sliderColor = sliderColor;
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
	protected void renderBg(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY) {
	}
	
	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		WidgetUtil.renderButtonLikeWidget(this, matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderBackground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		if (visible) {
			if (dragging) {
				sliderValue = (mouseX - (x + 4)) / (float) (width - 8);
				updateSlider();
			}
			RenderUtil.enableBlend();
			RenderUtil.defaultBlendFunc();
			GuiUtil.drawContinuousTexturedBox(matrixStack, WIDGETS_LOCATION, x + (int) (sliderValue * (width - 8)), y, 0, 66 + (isHovered() ? 20 : 0), 8, height, 200, 20, 2, 3, 2, 2, getBlitOffset(), getCurrentSliderColor(matrixStack, mouseX, mouseY, partialTicks));
			RenderUtil.disableBlend();
		}
		renderBg(matrixStack, minecraft, mouseX, mouseY);
	}
	
	@Override
	public void renderForeground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		WidgetUtil.renderText(this, matrixStack, minecraft, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public RGBA getCurrentBackgroundColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return sliderBackgroundColor;
	}
	
	public RGBA getCurrentSliderColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return sliderColor;
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
