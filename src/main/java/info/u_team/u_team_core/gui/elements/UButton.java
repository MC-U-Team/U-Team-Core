package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.BackgroundColorProvider;
import info.u_team.u_team_core.api.gui.PerspectiveRenderable;
import info.u_team.u_team_core.api.gui.TextProvider;
import info.u_team.u_team_core.api.gui.TextureProvider;
import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class UButton extends Button implements PerspectiveRenderable, BackgroundColorProvider, TextProvider {
	
	protected static final OnPress EMTPY_PRESSABLE = button -> {
	};
	
	protected static final CreateNarration DEFAULT_NARRATION = Button.DEFAULT_NARRATION;
	
	protected static final RGBA WHITE = RGBA.WHITE;
	protected static final RGBA LIGHT_GRAY = new RGBA(0xA0A0A0FF);
	
	protected TextureProvider buttonTextureProvider;
	protected RGBA buttonColor;
	
	protected RGBA textColor;
	protected RGBA disabledTextColor;
	
	public UButton(int x, int y, int width, int height, Component text) {
		this(x, y, width, height, text, EMTPY_PRESSABLE);
	}
	
	public UButton(int x, int y, int width, int height, Component text, OnPress pressable) {
		this(x, y, width, height, text, pressable, DEFAULT_NARRATION);
	}
	
	public UButton(int x, int y, int width, int height, Component text, OnPress pressable, CreateNarration narration) {
		super(x, y, width, height, text, pressable, narration);
		buttonTextureProvider = new WidgetTextureProvider(this, this::getYImage);
		buttonColor = WHITE;
		textColor = WHITE;
		disabledTextColor = LIGHT_GRAY;
	}
	
	public void setPressable(OnPress pressable) {
		onPress = pressable;
	}
	
	public void setPressable(Runnable runnable) {
		onPress = button -> runnable.run();
	}
	
	public void setCreateNarration(CreateNarration narration) {
		createNarration = narration;
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
	public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		WidgetUtil.renderButtonLikeWidget(this, buttonTextureProvider, poseStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderBackground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		renderBg(poseStack, Minecraft.getInstance(), mouseX, mouseY);
	}
	
	@Override
	public void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		WidgetUtil.renderText(this, poseStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public RGBA getCurrentBackgroundColor(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		return buttonColor;
	}
	
	@Override
	public Component getCurrentText() {
		return getMessage();
	}
	
	@Override
	public RGBA getCurrentTextColor(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		return active ? textColor : disabledTextColor;
	}
	
}
