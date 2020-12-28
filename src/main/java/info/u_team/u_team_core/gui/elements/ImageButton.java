package info.u_team.u_team_core.gui.elements;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class ImageButton extends UButton {
	
	@Deprecated
	protected ResourceLocation resource;
	
	protected ResourceLocation image;
	
	protected RGBA imageColor;
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation image) {
		this(x, y, width, height, image, EMTPY_PRESSABLE);
	}
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation image, IPressable pessable) {
		this(x, y, width, height, image, pessable, EMPTY_TOOLTIP);
	}
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation image, ITooltip tooltip) {
		this(x, y, width, height, image, EMTPY_PRESSABLE, tooltip);
	}
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation image, IPressable pessable, ITooltip tooltip) {
		super(x, y, width, height, StringTextComponent.EMPTY, pessable, tooltip);
		this.image = image;
		this.resource = image;
		imageColor = WHITE;
	}
	
	@Deprecated
	public ResourceLocation getResource() {
		return resource;
	}
	
	@Deprecated
	public void setResource(ResourceLocation resource) {
		this.resource = resource;
		this.image = resource;
	}
	
	public ResourceLocation getImage() {
		return image;
	}
	
	public void setImage(ResourceLocation image) {
		this.image = image;
		this.resource = image;
	}
	
	public RGBA getImageColor() {
		return imageColor;
	}
	
	public void setImageColor(RGBA imageColor) {
		this.imageColor = imageColor;
	}
	
	@Override
	protected void renderForeground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		minecraft.getTextureManager().bindTexture(getCurrentImage());
		
		RenderUtil.enableBlend();
		RenderUtil.defaultBlendFunc();
		GuiUtil.addTexturedColoredQuad(matrixStack, x + 2, x + width - 2, y + 2, y + height - 2, 0, 1, 0, 1, 0, getCurrentImageColor(matrixStack, mouseX, mouseY, partialTicks));
		RenderUtil.disableBlend();
	}
	
	@Deprecated
	protected void resetColor() {
		GL11.glColor4f(1, 1, 1, 1);
	}
	
	protected ResourceLocation getCurrentImage() {
		return image;
	}
	
	protected RGBA getCurrentImageColor(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return imageColor;
	}
}
