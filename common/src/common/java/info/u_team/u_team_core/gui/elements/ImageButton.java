package info.u_team.u_team_core.gui.elements;

import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ImageButton extends UButton {
	
	protected ResourceLocation image;
	
	protected RGBA imageColor;
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation image) {
		this(x, y, width, height, image, EMTPY_PRESSABLE);
	}
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation image, OnPress pessable) {
		super(x, y, width, height, Component.empty(), pessable);
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
	public void renderForeground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		final ResourceLocation image = getCurrentImage(guiGraphics, mouseX, mouseY, partialTick);
		final RGBA color = WidgetUtil.respectWidgetAlpha(this, getCurrentImageColor(guiGraphics, mouseX, mouseY, partialTick));
		
		RenderUtil.drawTexturedQuad(guiGraphics.pose(), x + 2, x + width - 2, y + 2, y + height - 2, 0, 1, 0, 1, 0, image, color);
	}
	
	public ResourceLocation getCurrentImage(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		return image;
	}
	
	public RGBA getCurrentImageColor(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		return imageColor;
	}
}
