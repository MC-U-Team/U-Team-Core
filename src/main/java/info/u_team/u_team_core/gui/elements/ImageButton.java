package info.u_team.u_team_core.gui.elements;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ImageButton extends UButton {
	
	protected ResourceLocation resource;
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation resource) {
		this(x, y, width, height, resource, EMTPY_PRESSABLE);
	}
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation resource, IPressable pressable) {
		super(x, y, width, height, "", pressable);
		this.resource = resource;
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
		resetColor();
		Minecraft.getInstance().getTextureManager().bindTexture(resource);
		innerBlit(x + 2, x + width - 2, y + 2, y + height - 2, 0, 0, 1, 0, 1);
	}
	
	protected void resetColor() {
		GL11.glColor4f(1, 1, 1, 1);
	}
}
