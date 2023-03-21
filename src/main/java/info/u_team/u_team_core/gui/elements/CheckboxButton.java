package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CheckboxButton extends UButton {
	
	protected static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");
	
	protected boolean checked;
	
	protected boolean drawText;
	protected boolean leftSideText;
	protected boolean dropShadow;
	
	public CheckboxButton(int x, int y, int width, int height, Component text, boolean checked, boolean drawText) {
		this(x, y, width, height, text, checked, drawText, EMTPY_PRESSABLE);
	}
	
	public CheckboxButton(int x, int y, int width, int height, Component text, boolean checked, boolean drawText, OnPress pessable) {
		super(x, y, width, height, text, pessable);
		this.checked = checked;
		this.drawText = drawText;
		leftSideText = false;
		dropShadow = false;
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public boolean isDrawText() {
		return drawText;
	}
	
	public void setDrawText(boolean drawText) {
		this.drawText = drawText;
	}
	
	public boolean isLeftSideText() {
		return leftSideText;
	}
	
	public void setLeftSideText(boolean leftSideText) {
		this.leftSideText = leftSideText;
	}
	
	public void toggle() {
		checked = !checked;
	}
	
	@Override
	public void onPress() {
		toggle();
		super.onPress();
	}
	
	@Override
	public void renderWidgetTexture(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		final RGBA color = WidgetUtil.respectWidgetAlpha(this, getCurrentBackgroundColor(poseStack, mouseY, mouseY, partialTicks));
		RenderUtil.drawTexturedQuad(poseStack, x, y, width, height, 20, 20, isHoveredOrFocused() ? 20 : 0, checked ? 20 : 0, 64, 64, 0, TEXTURE, color);
	}
	
	@Override
	public void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		if (drawText) {
			final Font font = getCurrentTextFont();
			
			final Component message = getCurrentText();
			if (message != CommonComponents.EMPTY) {
				final float xStart;
				final float yStart = y + (height - 8) / 2;
				
				if (leftSideText) {
					xStart = x - (font.width(message) + 4);
				} else {
					xStart = x + width + 4;
				}
				
				final int color = getCurrentTextColor(poseStack, mouseX, mouseY, partialTicks).getColorARGB();
				
				if (dropShadow) {
					font.drawShadow(poseStack, getCurrentText(), xStart, yStart, color);
				} else {
					font.draw(poseStack, getCurrentText(), xStart, yStart, color);
				}
			}
		}
	}
	
	@Override
	public void updateWidgetNarration(NarrationElementOutput output) {
		output.add(NarratedElementType.TITLE, createNarrationMessage());
		if (active) {
			if (isFocused()) {
				output.add(NarratedElementType.USAGE, Component.translatable("narration.checkbox.usage.focused"));
			} else {
				output.add(NarratedElementType.USAGE, Component.translatable("narration.checkbox.usage.hovered"));
			}
		}
	}
}
