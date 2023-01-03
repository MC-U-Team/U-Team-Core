package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.util.RGBA;
import net.minecraft.resources.ResourceLocation;

public class ImageToggleButton extends ImageButton {
	
	protected ResourceLocation toggleImage;
	
	protected RGBA toggleImageColor;
	
	protected boolean toggled;
	
	public ImageToggleButton(int x, int y, int width, int height, ResourceLocation image, ResourceLocation toggleImage, boolean toggled) {
		this(x, y, width, height, image, toggleImage, toggled, EMTPY_PRESSABLE);
	}
	
	public ImageToggleButton(int x, int y, int width, int height, ResourceLocation image, ResourceLocation toggleImage, boolean toggled, OnPress pessable) {
		super(x, y, width, height, image, pessable);
		this.toggleImage = toggleImage;
		toggleImageColor = WHITE;
		this.toggled = toggled;
	}
	
	public ResourceLocation getToggleImage() {
		return toggleImage;
	}
	
	public void setToggleImage(ResourceLocation toggleImage) {
		this.toggleImage = toggleImage;
	}
	
	public RGBA getToggleImageColor() {
		return toggleImageColor;
	}
	
	public void setToggleImageColor(RGBA toggleImageColor) {
		this.toggleImageColor = toggleImageColor;
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
	public ResourceLocation getCurrentImage(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		return toggled ? toggleImage : image;
	}
	
	@Override
	public RGBA getCurrentImageColor(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		return toggled ? toggleImageColor : imageColor;
	}
}
