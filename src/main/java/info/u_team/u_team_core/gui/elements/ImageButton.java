package info.u_team.u_team_core.gui.elements;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;

public class ImageButton extends UButton {
	
	protected ResourceLocation resource;
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation resource) {
		this(x, y, width, height, resource, EMTPY_PRESSABLE);
	}
	
	public ImageButton(int x, int y, int width, int height, ResourceLocation resource, IPressable pressable) {
		super(x, y, width, height, ITextComponent.func_241827_a_(null), pressable);
		this.resource = resource;
	}
	
	public ResourceLocation getResource() {
		return resource;
	}
	
	public void setResource(ResourceLocation resource) {
		this.resource = resource;
	}
	
	@Override
	public void func_230431_b_(MatrixStack matrixStack, int mouseX, int mouseY, float partial) {
		super.func_230431_b_(matrixStack, mouseX, mouseY, partial);
		resetColor();
		Minecraft.getInstance().getTextureManager().bindTexture(resource);
		func_238461_a_(matrixStack.getLast().getMatrix(), field_230690_l_ + 2, field_230690_l_ + field_230688_j_ - 2, field_230691_m_ + 2, field_230691_m_ + field_230689_k_ - 2, 0, 0, 1, 0, 1);
	}
	
	protected void resetColor() {
		GL11.glColor4f(1, 1, 1, 1);
	}
}
