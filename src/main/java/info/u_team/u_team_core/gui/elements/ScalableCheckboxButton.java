package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.Scalable;
import info.u_team.u_team_core.api.gui.ScaleProvider;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ScalableCheckboxButton extends CheckboxButton implements Scalable, ScaleProvider {
	
	protected float scale;
	
	public ScalableCheckboxButton(int x, int y, int width, int height, Component text, boolean checked, boolean drawText, float scale) {
		this(x, y, width, height, text, checked, drawText, scale, EMTPY_PRESSABLE);
	}
	
	public ScalableCheckboxButton(int x, int y, int width, int height, Component text, boolean checked, boolean drawText, float scale, OnPress pessable) {
		super(x, y, width, height, text, checked, drawText, pessable);
		this.scale = scale;
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
	public void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		if (drawText) {
			final Font font = getCurrentTextFont();
			
			final Component message = getCurrentText();
			if (message != CommonComponents.EMPTY) {
				final float currentScale = getCurrentScale(poseStack, mouseX, mouseY, partialTicks);
				
				final float positionFactor = 1 / currentScale;
				
				final float xStart;
				final float yStart = (y + ((int) (height - 8 * currentScale)) / 2) * positionFactor;
				
				if (leftSideText) {
					xStart = (x - ((font.width(message) * currentScale) + 4)) * positionFactor;
				} else {
					xStart = (x + width + 4) * positionFactor;
				}
				
				final int color = getCurrentTextColor(poseStack, mouseX, mouseY, partialTicks).getColorARGB();
				
				poseStack.pushPose();
				poseStack.scale(currentScale, currentScale, 0);
				
				if (dropShadow) {
					font.drawShadow(poseStack, getCurrentText(), xStart, yStart, color);
				} else {
					font.draw(poseStack, getCurrentText(), xStart, yStart, color);
				}
				
				poseStack.popPose();
			}
		}
	}
	
	@Override
	public float getCurrentScale(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		return scale;
	}
}
