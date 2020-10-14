package info.u_team.u_team_core.gui.elements;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.util.*;
import net.minecraft.util.ResourceLocation;

public class ActiveImageButton extends ImageButton {
	
	protected boolean active;
	
	protected RGBA activeColor;
	
	public ActiveImageButton(int x, int y, int width, int height, ResourceLocation resource, int activeColor) {
		this(x, y, width, height, resource, activeColor, EMTPY_PRESSABLE);
	}
	
	public ActiveImageButton(int x, int y, int width, int height, ResourceLocation resource, int activeColor, IPressable pressable) {
		super(x, y, width, height, resource, pressable);
		this.activeColor = new RGBA(activeColor);
	}
	
	public RGBA getActiveColorRGBA() {
		return activeColor;
	}
	
	public void setActiveColorRGBA(RGBA activeColor) {
		this.activeColor = activeColor;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partial) {
		GuiUtil.clearColor();
		if (active) {
			activeColor.glColor();
		}
		super.renderButton(matrixStack, mouseX, mouseY, partial);
		GL11.glColor4f(1, 1, 1, 1);
	}
}