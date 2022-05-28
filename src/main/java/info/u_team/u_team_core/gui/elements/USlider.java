package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.BackgroundColorProvider;
import info.u_team.u_team_core.api.gui.PerspectiveRenderable;
import info.u_team.u_team_core.api.gui.TextProvider;
import info.u_team.u_team_core.api.gui.TextureProvider;
import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public non-sealed class USlider extends AbstractSliderLogic implements PerspectiveRenderable, BackgroundColorProvider, TextProvider {
	
	protected static final OnSliderChange EMPTY_SLIDER = AbstractSliderLogic.EMPTY_SLIDER;
	
	protected static final RGBA WHITE = UButton.WHITE;
	protected static final RGBA LIGHT_GRAY = UButton.LIGHT_GRAY;
	
	protected final boolean isInContainer;
	
	protected TextureProvider sliderBackgroundTextureProvider;
	protected RGBA sliderBackgroundColor;
	
	protected TextureProvider sliderTextureProvider;
	protected RGBA sliderColor;
	
	public USlider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, boolean isInContainer) {
		this(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, isInContainer, EMPTY_SLIDER);
	}
	
	public USlider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, boolean isInContainer, OnSliderChange slider) {
		this(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, isInContainer, slider, EMPTY_TOOLTIP);
	}
	
	public USlider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, boolean isInContainer, OnTooltip tooltip) {
		this(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, isInContainer, EMPTY_SLIDER, tooltip);
	}
	
	public USlider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, boolean isInContainer, OnSliderChange slider, OnTooltip tooltip) {
		super(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, slider, tooltip);
		this.isInContainer = isInContainer;
		sliderBackgroundTextureProvider = new WidgetTextureProvider(this, hovered -> 0);
		sliderBackgroundColor = WHITE;
		sliderTextureProvider = new WidgetTextureProvider(this, hovered -> hovered ? 2 : 1);
		sliderColor = WHITE;
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
	
	@Override
	public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		WidgetUtil.renderButtonLikeWidget(this, sliderBackgroundTextureProvider, poseStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderBackground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		renderBg(poseStack, Minecraft.getInstance(), mouseX, mouseY);
		if (visible) {
			RenderUtil.drawContinuousTexturedBox(poseStack, x + (int) (value * (width - 8)), y, sliderTextureProvider.getU(), sliderTextureProvider.getV(), 8, height, sliderBackgroundTextureProvider.getWidth(), sliderTextureProvider.getHeight(), 2, 3, 2, 2, getBlitOffset(), sliderTextureProvider.getTexture(), getCurrentSliderColor(poseStack, mouseX, mouseY, partialTicks));
		}
	}
	
	@Override
	public void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		WidgetUtil.renderText(this, poseStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderToolTip(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		renderToolTip(poseStack, mouseX, mouseY);
	}
	
	@Override
	public RGBA getCurrentBackgroundColor(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		return sliderBackgroundColor;
	}
	
	public RGBA getCurrentSliderColor(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		return sliderColor;
	}
	
	@Override
	public Component getCurrentText() {
		return getMessage();
	}
	
	@Override
	public RGBA getCurrentTextColor(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		return active ? textColor : disabledTextColor;
	}
	
	@Override
	public void onClick(double mouseX, double mouseY) {
		changeSliderValue(mouseX);
		if (isInContainer) {
			dragging = true;
		}
	}
	
	@Override
	protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
		if (!isInContainer) {
			changeSliderValue(mouseX);
		}
	}
	
	@Override
	public void onRelease(double mouseX, double mouseY) {
		if (isHoveredOrFocused()) {
			super.playDownSound(Minecraft.getInstance().getSoundManager());
		}
		if (isInContainer) {
			dragging = false;
		}
	}
	
	@Override
	protected void renderBg(PoseStack poseStack, Minecraft minecraft, int mouseX, int mouseY) {
		if (isInContainer && visible && dragging) {
			changeSliderValue(mouseX);
		}
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		final boolean flag = keyCode == 263;
		if (flag || keyCode == 262) {
			final float direction = flag ? -1.0F : 1.0F;
			setSliderValue(value + direction / (width - 8));
		}
		return false;
	}
	
	@Override
	public void playDownSound(SoundManager handler) {
	}
	
	protected void changeSliderValue(double mouseX) {
		setSliderValue((mouseX - (x + 4)) / (width - 8));
	}
	
	protected void setSliderValue(double newValue) {
		final double oldValue = value;
		value = Mth.clamp(newValue, 0, 1);
		if (oldValue != value) {
			updateSlider();
		}
	}
}
