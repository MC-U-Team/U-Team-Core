package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.util.RGBA;
import net.minecraft.util.ResourceLocation;

public class ImageActivatableButton extends ImageButton {
	
	protected boolean activated;
	
	protected RGBA activatedColor;
	
	public ImageActivatableButton(int x, int y, int width, int height, ResourceLocation image, boolean activated, RGBA activatedColor) {
		this(x, y, width, height, image, activated, activatedColor, EMTPY_PRESSABLE);
	}
	
	public ImageActivatableButton(int x, int y, int width, int height, ResourceLocation image, boolean activated, RGBA activatedColor, IPressable pessable) {
		this(x, y, width, height, image, activated, activatedColor, pessable, EMPTY_TOOLTIP);
	}
	
	public ImageActivatableButton(int x, int y, int width, int height, ResourceLocation image, boolean activated, RGBA activatedColor, ITooltip tooltip) {
		this(x, y, width, height, image, activated, activatedColor, EMTPY_PRESSABLE, tooltip);
	}
	
	public ImageActivatableButton(int x, int y, int width, int height, ResourceLocation image, boolean activated, RGBA activatedColor, IPressable pessable, ITooltip tooltip) {
		super(x, y, width, height, image, pessable, tooltip);
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
	protected RGBA getCurrentButtonColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return activated ? activatedColor : buttonColor;
	}
	
}
