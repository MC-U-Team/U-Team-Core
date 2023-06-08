package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.TextureProvider;
import info.u_team.u_team_core.util.FontUtil;
import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CheckboxButton extends UButton {
	
	protected static final ResourceLocation TEXTURE = Checkbox.TEXTURE;
	
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
		buttonTextureProvider = new TextureProvider() {
			
			@Override
			public ResourceLocation getTexture() {
				return TEXTURE;
			}
			
			@Override
			public int getU() {
				return isHoveredOrFocused() ? 20 : 0;
			}
			
			@Override
			public int getV() {
				return CheckboxButton.this.checked ? 20 : 0;
			}
			
			@Override
			public int getWidth() {
				return 20;
			}
			
			@Override
			public int getHeight() {
				return 20;
			}
		};
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
	public void renderWidgetTexture(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		final RGBA color = WidgetUtil.respectWidgetAlpha(this, getCurrentBackgroundColor(guiGraphics, mouseY, mouseY, partialTick));
		RenderUtil.drawTexturedQuad(guiGraphics.pose(), x, y, width, height, buttonTextureProvider.getWidth(), buttonTextureProvider.getHeight(), buttonTextureProvider.getU(), buttonTextureProvider.getV(), 64, 64, 0, buttonTextureProvider.getTexture(), color);
	}
	
	@Override
	public void renderForeground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		if (drawText) {
			final Font font = getCurrentTextFont();
			
			final Component message = getCurrentText();
			if (message != CommonComponents.EMPTY) {
				final float currentScale = getCurrentScale(guiGraphics, mouseX, mouseY, partialTick);
				
				final float positionFactor = 1 / currentScale;
				
				final float xStart;
				final float yStart = (y + ((int) (height - 8 * currentScale)) / 2) * positionFactor;
				
				if (leftSideText) {
					xStart = (x - ((font.width(message) * currentScale) + 4)) * positionFactor;
				} else {
					xStart = (x + width + 4) * positionFactor;
				}
				
				final int color = WidgetUtil.respectWidgetAlpha(this, getCurrentTextColor(guiGraphics, mouseY, mouseY, partialTick)).getColorARGB();
				
				final PoseStack poseStack = guiGraphics.pose();
				poseStack.pushPose();
				poseStack.scale(currentScale, currentScale, 0);
				
				FontUtil.drawString(guiGraphics, font, getCurrentText(), xStart, yStart, color, dropShadow);
				
				poseStack.popPose();
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
