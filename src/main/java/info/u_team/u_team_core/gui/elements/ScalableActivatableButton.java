package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.util.RGBA;
import net.minecraft.util.text.ITextComponent;

public class ScalableActivatableButton extends ScalableButton {
	
	protected boolean activated;
	
	protected RGBA activatedColor;
	
	public ScalableActivatableButton(int x, int y, int width, int height, ITextComponent text, float scale, boolean activated, RGBA activatedColor) {
		this(x, y, width, height, text, scale, activated, activatedColor, EMTPY_PRESSABLE);
	}
	
	public ScalableActivatableButton(int x, int y, int width, int height, ITextComponent text, float scale, boolean activated, RGBA activatedColor, IPressable pessable) {
		this(x, y, width, height, text, scale, activated, activatedColor, pessable, EMPTY_TOOLTIP);
	}
	
	public ScalableActivatableButton(int x, int y, int width, int height, ITextComponent text, float scale, boolean activated, RGBA activatedColor, ITooltip tooltip) {
		this(x, y, width, height, text, scale, activated, activatedColor, EMTPY_PRESSABLE, tooltip);
	}
	
	public ScalableActivatableButton(int x, int y, int width, int height, ITextComponent text, float scale, boolean activated, RGBA activatedColor, IPressable pessable, ITooltip tooltip) {
		super(x, y, width, height, text, scale, pessable, tooltip);
		this.activated = activated;
		this.activatedColor = activatedColor;
	}
	
	public boolean isActivated() {
		return activated;
	}
	
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	public RGBA getActivatedColor() {
		return activatedColor;
	}
	
	public void setActivatedColor(RGBA activatedColor) {
		this.activatedColor = activatedColor;
	}
	
	@Override
	public RGBA getCurrentBackgroundColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return activated ? activatedColor : buttonColor;
	}
}
