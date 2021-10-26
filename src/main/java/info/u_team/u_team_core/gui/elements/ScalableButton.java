package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.Scalable;
import info.u_team.u_team_core.api.gui.ScaleProvider;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.network.chat.Component;

public class ScalableButton extends UButton implements Scalable, ScaleProvider {
	
	protected float scale;
	
	public ScalableButton(int x, int y, int width, int height, Component text, float scale) {
		this(x, y, width, height, text, scale, EMTPY_PRESSABLE);
	}
	
	public ScalableButton(int x, int y, int width, int height, Component text, float scale, OnPress pessable) {
		this(x, y, width, height, text, scale, pessable, EMPTY_TOOLTIP);
	}
	
	public ScalableButton(int x, int y, int width, int height, Component text, float scale, OnTooltip tooltip) {
		this(x, y, width, height, text, scale, EMTPY_PRESSABLE, tooltip);
	}
	
	public ScalableButton(int x, int y, int width, int height, Component text, float scale, OnPress pessable, OnTooltip tooltip) {
		super(x, y, width, height, text, pessable, tooltip);
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
		WidgetUtil.renderScaledText(this, poseStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public float getCurrentScale(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		return scale;
	}
}
