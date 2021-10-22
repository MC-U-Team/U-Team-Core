package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.IBackgroundColorProvider;
import info.u_team.u_team_core.api.gui.IPerspectiveRenderable;
import info.u_team.u_team_core.api.gui.ITextProvider;
import info.u_team.u_team_core.api.gui.ITextureProvider;
import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraftforge.fmlclient.gui.widget.Slider;

public class USlider extends Slider implements IPerspectiveRenderable, IBackgroundColorProvider, ITextProvider {
	
	protected static final ISlider EMTPY_SLIDER = slider -> {
	};
	
	protected static final OnTooltip EMPTY_TOOLTIP = UButton.EMPTY_TOOLTIP;
	
	protected static final RGBA WHITE = UButton.WHITE;
	protected static final RGBA LIGHT_GRAY = UButton.LIGHT_GRAY;
	
	protected final boolean isInContainer;
	
	protected ITextureProvider sliderBackgroundTextureProvider;
	
	protected RGBA sliderBackgroundColor;
	protected RGBA sliderColor;
	
	protected RGBA textColor;
	protected RGBA disabledTextColor;
	
	public USlider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, boolean isInContainer) {
		this(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, isInContainer, EMTPY_SLIDER);
	}
	
	public USlider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, boolean isInContainer, ISlider slider) {
		this(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, isInContainer, slider, EMPTY_TOOLTIP);
	}
	
	public USlider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, boolean isInContainer, OnTooltip tooltip) {
		this(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, isInContainer, EMTPY_SLIDER, tooltip);
	}
	
	public USlider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, boolean isInContainer, ISlider slider, OnTooltip tooltip) {
		super(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, UButton.EMTPY_PRESSABLE, slider);
		this.isInContainer = isInContainer;
		onTooltip = tooltip;
		sliderBackgroundTextureProvider = new WidgetTextureProvider(this, this::getYImage);
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
	
	public void setTooltip(OnTooltip tooltip) {
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
	public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		WidgetUtil.renderButtonLikeWidget(this, sliderBackgroundTextureProvider, matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderBackground(PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		renderBg(matrixStack, minecraft, mouseX, mouseY);
		if (visible) {
			RenderUtil.drawContinuousTexturedBox(matrixStack, x + (int) (sliderValue * (width - 8)), y, 0, 66 + (isHovered() ? 20 : 0), 8, height, 200, 20, 2, 3, 2, 2, getBlitOffset(), WIDGETS_LOCATION, getCurrentSliderColor(matrixStack, mouseX, mouseY, partialTicks));
		}
	}
	
	@Override
	public void renderForeground(PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		WidgetUtil.renderText(this, matrixStack, minecraft, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderToolTip(PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		renderToolTip(matrixStack, mouseX, mouseY);
	}
	
	@Override
	public RGBA getCurrentBackgroundColor(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return sliderBackgroundColor;
	}
	
	public RGBA getCurrentSliderColor(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return sliderColor;
	}
	
	@Override
	public Component getCurrentText() {
		return getMessage();
	}
	
	@Override
	public RGBA getCurrentTextColor(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
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
		if (isHovered()) {
			super.playDownSound(Minecraft.getInstance().getSoundManager());
		}
		if (isInContainer) {
			dragging = false;
		}
	}
	
	@Override
	protected void renderBg(PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY) {
		if (isInContainer && visible && dragging) {
			changeSliderValue(mouseX);
		}
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		final var flag = keyCode == 263;
		if (flag || keyCode == 262) {
			final var direction = flag ? -1.0F : 1.0F;
			setSliderValue(sliderValue + direction / (width - 8));
		}
		return false;
	}
	
	@Override
	public void playDownSound(SoundManager handler) {
	}
	
	@Override
	protected MutableComponent createNarrationMessage() {
		return new TranslatableComponent("gui.narrate.slider", getMessage());
	}
	
	protected void changeSliderValue(double mouseX) {
		setSliderValue((mouseX - (x + 4)) / (width - 8));
	}
	
	protected void setSliderValue(double value) {
		final var oldValue = sliderValue;
		sliderValue = Mth.clamp(value, 0, 1);
		if (oldValue != sliderValue) {
			updateSlider();
		}
	}
}
