package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class ImageButton extends UButton {
	
	protected ResourceLocation image;
	
	protected RGBA imageColor;
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation image) {
		this(x, y, width, height, image, EMTPY_PRESSABLE);
	}
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation image, OnPress pessable) {
		this(x, y, width, height, image, pessable, EMPTY_TOOLTIP);
	}
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation image, OnTooltip tooltip) {
		this(x, y, width, height, image, EMTPY_PRESSABLE, tooltip);
	}
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation image, OnPress pessable, OnTooltip tooltip) {
		super(x, y, width, height, TextComponent.EMPTY, pessable, tooltip);
		this.image = image;
		imageColor = WHITE;
	}
	
	public ResourceLocation getImage() {
		return image;
	}
	
	public void setImage(ResourceLocation image) {
		this.image = image;
	}
	
	public RGBA getImageColor() {
		return imageColor;
	}
	
	public void setImageColor(RGBA imageColor) {
		this.imageColor = imageColor;
	}
	
	@Override
	public void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		RenderUtil.drawTexturedQuad(poseStack, x + 2, x + width - 2, y + 2, y + height - 2, 0, 1, 0, 1, 0, getCurrentImage(poseStack, mouseX, mouseY, partialTicks), getCurrentImageColor(poseStack, mouseX, mouseY, partialTicks));
	}
	
	public ResourceLocation getCurrentImage(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		return image;
	}
	
	public RGBA getCurrentImageColor(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		return imageColor;
	}
}
