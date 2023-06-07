package info.u_team.u_team_core.gui.elements;

import info.u_team.u_team_core.api.gui.BackgroundColorProvider;
import info.u_team.u_team_core.api.gui.Scalable;
import info.u_team.u_team_core.api.gui.ScaleProvider;
import info.u_team.u_team_core.api.gui.TextProvider;
import info.u_team.u_team_core.api.gui.TextureProvider;
import info.u_team.u_team_core.api.gui.WidgetRenderable;
import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public non-sealed class USlider extends AbstractSliderLogic implements WidgetRenderable, BackgroundColorProvider, TextProvider, Scalable, ScaleProvider {
	
	protected static final OnSliderChange EMPTY_SLIDER = AbstractSliderLogic.EMPTY_SLIDER;
	
	protected static final RGBA WHITE = UButton.WHITE;
	protected static final RGBA LIGHT_GRAY = UButton.LIGHT_GRAY;
	
	protected TextureProvider sliderBackgroundTextureProvider;
	protected RGBA sliderBackgroundColor;
	
	protected TextureProvider sliderTextureProvider;
	protected RGBA sliderColor;
	
	protected RGBA textColor;
	protected RGBA disabledTextColor;
	
	protected float scale = 1;
	
	public USlider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription) {
		this(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, EMPTY_SLIDER);
	}
	
	public USlider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, OnSliderChange slider) {
		super(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, slider);
		sliderBackgroundTextureProvider = new WidgetTextureProvider(SLIDER_LOCATION, this::getTextureY);
		sliderBackgroundColor = WHITE;
		sliderTextureProvider = new WidgetTextureProvider(SLIDER_LOCATION, this::getHandleTextureY);
		sliderColor = WHITE;
		textColor = WHITE;
		disabledTextColor = LIGHT_GRAY;
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
	public float getScale() {
		return scale;
	}
	
	@Override
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		WidgetUtil.renderWidget(this, guiGraphics, mouseX, mouseY, partialTick);
	}
	
	@Override
	public void renderWidgetTexture(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		WidgetUtil.renderButtonLikeTexture(this, sliderBackgroundTextureProvider, guiGraphics, mouseX, mouseY, partialTick);
	}
	
	@Override
	public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		final RGBA color = WidgetUtil.respectWidgetAlpha(this, getCurrentSliderColor(guiGraphics, mouseX, mouseY, partialTick));
		RenderUtil.drawContinuousTexturedBox(guiGraphics.pose(), x + (int) (value * (width - 8)), y, sliderTextureProvider.getU(), sliderTextureProvider.getV(), 8, height, sliderTextureProvider.getWidth(), sliderTextureProvider.getHeight(), 2, 3, 2, 2, 0, sliderTextureProvider.getTexture(), color);
	}
	
	@Override
	public void renderForeground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		WidgetUtil.renderText(this, guiGraphics, mouseX, mouseY, partialTick);
	}
	
	@Override
	public RGBA getCurrentBackgroundColor(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		return sliderBackgroundColor;
	}
	
	public RGBA getCurrentSliderColor(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		return sliderColor;
	}
	
	@Override
	public Component getCurrentText() {
		return getMessage();
	}
	
	@Override
	public RGBA getCurrentTextColor(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		return active ? textColor : disabledTextColor;
	}
	
	@Override
	public float getCurrentScale(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		return scale;
	}
}
