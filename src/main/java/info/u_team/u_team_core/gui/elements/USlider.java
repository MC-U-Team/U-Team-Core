package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.api.gui.IBackgroundColorProvider;
import info.u_team.u_team_core.api.gui.IPerspectiveRenderable;
import info.u_team.u_team_core.api.gui.ITextProvider;
import info.u_team.u_team_core.api.gui.ITextureProvider;
import info.u_team.u_team_core.util.GuiUtil;
import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.widget.Slider;

public class USlider extends Slider implements IPerspectiveRenderable, IBackgroundColorProvider, ITextProvider {
	
	protected static final ISlider EMTPY_SLIDER = slider -> {
	};
	
	protected static final ITooltip EMPTY_TOOLTIP = UButton.EMPTY_TOOLTIP;
	
	protected static final RGBA WHITE = UButton.WHITE;
	protected static final RGBA LIGHT_GRAY = UButton.LIGHT_GRAY;
	
	protected final boolean isInContainer;
	
	protected ITextureProvider sliderBackgroundTextureProvider;
	
	protected RGBA sliderBackgroundColor;
	protected RGBA sliderColor;
	
	protected RGBA textColor;
	protected RGBA disabledTextColor;
	
	public USlider(int x, int y, int width, int height, ITextComponent prefix, ITextComponent suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, boolean isInContainer) {
		this(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, isInContainer, EMTPY_SLIDER);
	}
	
	public USlider(int x, int y, int width, int height, ITextComponent prefix, ITextComponent suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, boolean isInContainer, ISlider slider) {
		this(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, isInContainer, slider, EMPTY_TOOLTIP);
	}
	
	public USlider(int x, int y, int width, int height, ITextComponent prefix, ITextComponent suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, boolean isInContainer, ITooltip tooltip) {
		this(x, y, width, height, prefix, suffix, minValue, maxValue, value, decimalPrecision, drawDescription, isInContainer, EMTPY_SLIDER, tooltip);
	}
	
	public USlider(int x, int y, int width, int height, ITextComponent prefix, ITextComponent suffix, double minValue, double maxValue, double value, boolean decimalPrecision, boolean drawDescription, boolean isInContainer, ISlider slider, ITooltip tooltip) {
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
	public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		WidgetUtil.renderButtonLikeWidget(this, sliderBackgroundTextureProvider, matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderBackground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		renderBg(matrixStack, minecraft, mouseX, mouseY);
		if (visible) {
			RenderUtil.enableBlend();
			RenderUtil.defaultBlendFunc();
			GuiUtil.drawContinuousTexturedBox(matrixStack, WIDGETS_LOCATION, x + (int) (sliderValue * (width - 8)), y, 0, 66 + (isHovered() ? 20 : 0), 8, height, 200, 20, 2, 3, 2, 2, getBlitOffset(), getCurrentSliderColor(matrixStack, mouseX, mouseY, partialTicks));
			RenderUtil.disableBlend();
		}
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
			super.playDownSound(Minecraft.getInstance().getSoundHandler());
		}
		if (isInContainer) {
			dragging = false;
		}
	}
	
	@Override
	protected void renderBg(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY) {
		if (isInContainer && visible && dragging) {
			changeSliderValue(mouseX);
		}
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		final boolean flag = keyCode == 263;
		if (flag || keyCode == 262) {
			final float direction = flag ? -1.0F : 1.0F;
			setSliderValue(sliderValue + direction / (width - 8));
		}
		return false;
	}
	
	@Override
	public void playDownSound(SoundHandler handler) {
	}
	
	@Override
	protected IFormattableTextComponent getNarrationMessage() {
		return new TranslationTextComponent("gui.narrate.slider", getMessage());
	}
	
	protected void changeSliderValue(double mouseX) {
		this.setSliderValue((mouseX - (x + 4)) / (width - 8));
	}
	
	protected void setSliderValue(double value) {
		final double oldValue = sliderValue;
		sliderValue = MathHelper.clamp(value, 0, 1);
		if (oldValue != sliderValue) {
			updateSlider();
		}
	}
}
