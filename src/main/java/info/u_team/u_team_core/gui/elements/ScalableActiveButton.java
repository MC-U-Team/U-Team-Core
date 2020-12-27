package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.util.RGBA;
import net.minecraft.util.text.ITextComponent;

public class ScalableActiveButton extends ScalableButton {
	
	protected boolean active;
	
	protected RGBA activeColor;
	
	public ScalableActiveButton(int x, int y, int width, int height, ITextComponent display, float scale, boolean active, RGBA activeColor) {
		this(x, y, width, height, display, scale, active, activeColor, EMTPY_PRESSABLE);
	}
	
	public ScalableActiveButton(int x, int y, int width, int height, ITextComponent display, float scale, boolean active, RGBA activeColor, IPressable pessable) {
		this(x, y, width, height, display, scale, active, activeColor, pessable, EMPTY_TOOLTIP);
	}
	
	public ScalableActiveButton(int x, int y, int width, int height, ITextComponent display, float scale, boolean active, RGBA activeColor, ITooltip tooltip) {
		this(x, y, width, height, display, scale, active, activeColor, EMTPY_PRESSABLE, tooltip);
	}
	
	public ScalableActiveButton(int x, int y, int width, int height, ITextComponent display, float scale, boolean active, RGBA activeColor, IPressable pessable, ITooltip tooltip) {
		super(x, y, width, height, display, scale, pessable, tooltip);
		this.active = active;
		this.activeColor = activeColor;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public RGBA getActiveColor() {
		return activeColor;
	}
	
	public void setActiveColor(RGBA activeColor) {
		this.activeColor = activeColor;
	}
	
	@Override
	protected RGBA getCurrentButtonColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return active ? activeColor : buttonColor;
	}
}
