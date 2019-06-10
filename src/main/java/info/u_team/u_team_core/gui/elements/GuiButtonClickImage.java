package info.u_team.u_team_core.gui.elements;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class GuiButtonClickImage extends GuiButtonClick {
	
	protected ResourceLocation resource;
	protected int color, hovercolor;
	
	public GuiButtonClickImage(int x, int y, int width, int height, ResourceLocation resource) {
		this(x, y, width, height, resource, -1, -1);
	}
	
	public GuiButtonClickImage(int x, int y, int width, int height, ResourceLocation resource, int color, int hovercolor) {
		super(x, y, width, height, "");
		this.resource = resource;
		this.color = color;
		this.hovercolor = hovercolor;
	}
	
	public void setResource(ResourceLocation resource) {
		this.resource = resource;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public void setHoverColor(int hovercolor) {
		this.hovercolor = hovercolor;
	}
	
	@Override
	public void renderButton(int mouseX, int mouseY, float partial) {
		if (visible) {
			super.renderButton(mouseX, mouseY, partial);
			if (isHovered) {
				color(hovercolor);
			} else {
				color(color);
			}
			Minecraft.getInstance().getTextureManager().bindTexture(resource);
			blit(x + 2, y + 2, 0, 0, 1, 1, width - 4, height - 4, 1, 1);
//			Gui.drawScaledCustomSizeModalRect(x + 2, y + 2, 0, 0, 1, 1, width - 4, height - 4, 1, 1);
		}
	}
	
	protected void color(int color) {
		if (color == -1) {
			GL11.glColor4f(1F, 1F, 1F, 1F);
			return;
		}
		float red = (color >> 24 & 255) / 255F;
		float green = (color >> 16 & 255) / 255F;
		float blue = (color >> 8 & 255) / 255F;
		float alpha = (color & 255) / 255F;
		GL11.glColor4f(red, green, blue, alpha);
	}
	
}
