package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.util.RGBA;
import net.minecraft.util.ResourceLocation;

public class ImageToggleButton extends ImageButton {
	
	protected ResourceLocation toggleImage;
	
	protected RGBA toggleImageColor;
	
	protected boolean toggled;
	
	public ImageToggleButton(int x, int y, int width, int height, ResourceLocation image, ResourceLocation toggleImage, boolean toggled) {
		this(x, y, width, height, image, toggleImage, toggled, EMTPY_PRESSABLE);
	}
	
	public ImageToggleButton(int x, int y, int width, int height, ResourceLocation image, ResourceLocation toggleImage, boolean toggled, IPressable pessable) {
		this(x, y, width, height, image, toggleImage, toggled, pessable, EMPTY_TOOLTIP);
	}
	
	public ImageToggleButton(int x, int y, int width, int height, ResourceLocation image, ResourceLocation toggleImage, boolean toggled, ITooltip tooltip) {
		this(x, y, width, height, image, toggleImage, toggled, EMTPY_PRESSABLE, tooltip);
	}
	
	public ImageToggleButton(int x, int y, int width, int height, ResourceLocation image, ResourceLocation toggleImage, boolean toggled, IPressable pessable, ITooltip tooltip) {
		super(x, y, width, height, image, pessable, tooltip);
		this.toggleImage = toggleImage;
		toggleImageColor = WHITE;
		this.toggled = toggled;
	}
	
	public boolean isToggled() {
		return toggled;
	}
	
	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}
	
	public void toggle() {
		toggled = !toggled;
	}
	
	@Override
	public void onPress() {
		toggle();
		super.onPress();
	}
	
	@Override
	protected ResourceLocation getCurrentImage() {
		return toggled ? toggleImage : image;
	}
	
	@Override
	protected RGBA getCurrentImageColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return toggled ? toggleImageColor : imageColor;
	}
}
