package info.u_team.u_team_core.gui.elements;

import info.u_team.u_team_core.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class ImageButton extends UButton {
	
	protected ResourceLocation resource;
	
	protected RGBA color, hoverColor;
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation resource) {
		this(x, y, width, height, resource, EMTPY_PRESSABLE);
	}
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation resource, IPressable pressable) {
		this(x, y, width, height, resource, ColorUtil.WHITE_RGBA, ColorUtil.WHITE_RGBA, pressable);
	}
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation resource, int color, int hoverColor) {
		this(x, y, width, height, resource, color, hoverColor, EMTPY_PRESSABLE);
	}
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation resource, int color, int hoverColor, IPressable pressable) {
		super(x, y, width, height, "", pressable);
		this.resource = resource;
		this.color = new RGBA(color);
		this.hoverColor = new RGBA(hoverColor);
	}
	
	public RGBA getColor() {
		return color;
	}
	
	public void setColor(RGBA color) {
		this.color = color;
	}
	
	public RGBA getHoverColor() {
		return hoverColor;
	}
	
	public void setHoverColor(RGBA hoverColor) {
		this.hoverColor = hoverColor;
	}
	
	public ResourceLocation getResource() {
		return resource;
	}
	
	public void setResource(ResourceLocation resource) {
		this.resource = resource;
	}
	
	@Override
	public void renderButton(int mouseX, int mouseY, float partial) {
		super.renderButton(mouseX, mouseY, partial);
		if (isHovered()) {
			hoverColor.glColor();
		} else {
			color.glColor();
		}
		Minecraft.getInstance().getTextureManager().bindTexture(resource);
		innerBlit(x + 2, x + width - 2, y + 2, y + height - 2, 0, 0, 1, 0, 1);
	}
	
	@Deprecated
	public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
		float f = 1.0F / tileWidth;
		float f1 = 1.0F / tileHeight;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) ((v + (float) vHeight) * f1)).endVertex();
		bufferbuilder.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + (float) uWidth) * f), (double) ((v + (float) vHeight) * f1)).endVertex();
		bufferbuilder.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + (float) uWidth) * f), (double) (v * f1)).endVertex();
		bufferbuilder.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
		tessellator.draw();
	}
}
