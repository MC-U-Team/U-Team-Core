package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;

public class CheckboxButton extends UButton {
	
	protected static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");
	
	protected boolean checked;
	
	protected boolean drawText;
	protected boolean leftSideText;
	protected boolean dropShadow;
	
	public CheckboxButton(int x, int y, int width, int height, ITextComponent text, boolean checked, boolean drawText) {
		this(x, y, width, height, text, checked, drawText, EMTPY_PRESSABLE);
	}
	
	public CheckboxButton(int x, int y, int width, int height, ITextComponent text, boolean checked, boolean drawText, IPressable pessable) {
		this(x, y, width, height, text, checked, drawText, pessable, EMPTY_TOOLTIP);
	}
	
	public CheckboxButton(int x, int y, int width, int height, ITextComponent text, boolean checked, boolean drawText, ITooltip tooltip) {
		this(x, y, width, height, text, checked, drawText, EMTPY_PRESSABLE, tooltip);
	}
	
	public CheckboxButton(int x, int y, int width, int height, ITextComponent text, boolean checked, boolean drawText, IPressable pessable, ITooltip tooltip) {
		super(x, y, width, height, text, pessable, tooltip);
		this.checked = checked;
		this.drawText = drawText;
		leftSideText = false;
		dropShadow = false;
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public boolean isDrawText() {
		return drawText;
	}
	
	public void setDrawText(boolean drawText) {
		this.drawText = drawText;
	}
	
	public boolean isLeftSideText() {
		return leftSideText;
	}
	
	public void setLeftSideText(boolean leftSideText) {
		this.leftSideText = leftSideText;
	}
	
	public void toggle() {
		checked = !checked;
	}
	
	@Override
	public void onPress() {
		toggle();
		super.onPress();
	}
	
	@Override
	public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		final Minecraft minecraft = Minecraft.getInstance();
		
		minecraft.getTextureManager().bindTexture(TEXTURE);
		
		RenderUtil.enableBlend();
		RenderUtil.defaultBlendFunc();
		GuiUtil.drawTexturedColoredQuad(matrixStack, x, y, width, height, 20, 20, isHovered() ? 20 : 0, checked ? 20 : 0, 64, 64, 0, getCurrentBackgroundColor(matrixStack, mouseX, mouseY, partialTicks));
		RenderUtil.disableBlend();
		
		renderBackground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
		renderForeground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderForeground(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		if (drawText) {
			final FontRenderer fontRenderer = minecraft.fontRenderer;
			
			final ITextComponent message = getCurrentText();
			if (message != StringTextComponent.EMPTY) {
				final float xStart;
				final float yStart = y + (height - 8) / 2;
				
				if (leftSideText) {
					xStart = x - (fontRenderer.getStringPropertyWidth(message) + 4);
				} else {
					xStart = x + width + 4;
				}
				
				final int color = getCurrentTextColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB();
				
				if (dropShadow) {
					fontRenderer.drawTextWithShadow(matrixStack, getCurrentText(), xStart, yStart, color);
				} else {
					fontRenderer.drawText(matrixStack, getCurrentText(), xStart, yStart, color);
				}
			}
		}
	}
}
