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
		sliderBackgroundTextureProvider = this::getSprite;
		sliderBackgroundColor = WHITE;
		sliderTextureProvider = this::getHandleSprite;
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
	public void renderBehind(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		RenderUtil.setShaderColor(WidgetUtil.respectWidgetAlpha(this, getCurrentSliderColor(guiGraphics, mouseX, mouseY, partialTick)));
		guiGraphics.blitSprite(sliderTextureProvider.getTexture(), x + (int) (value * (width - 8)), y, 8, height);
		RenderUtil.resetShaderColor();
	}
	
	@Override
	public void renderBefore(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
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
