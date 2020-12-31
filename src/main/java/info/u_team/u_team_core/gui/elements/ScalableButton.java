package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.api.gui.*;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;

public class ScalableButton extends UButton implements IScaleable, IScaleProvider {
	
	protected float scale;
	
	public ScalableButton(int x, int y, int width, int height, ITextComponent text, float scale) {
		this(x, y, width, height, text, scale, EMTPY_PRESSABLE);
	}
	
	public ScalableButton(int x, int y, int width, int height, ITextComponent text, float scale, IPressable pessable) {
		this(x, y, width, height, text, scale, pessable, EMPTY_TOOLTIP);
	}
	
	public ScalableButton(int x, int y, int width, int height, ITextComponent text, float scale, ITooltip tooltip) {
		this(x, y, width, height, text, scale, EMTPY_PRESSABLE, tooltip);
	}
	
	public ScalableButton(int x, int y, int width, int height, ITextComponent text, float scale, IPressable pessable, ITooltip tooltip) {
		super(x, y, width, height, text, pessable, tooltip);
		this.scale = scale;
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	@Override
	public void renderForeground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		WidgetUtil.renderScaledText(this, matrixStack, minecraft, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public float getCurrentScale(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return scale;
	}
}
