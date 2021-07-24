package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.util.GuiUtil;
import info.u_team.u_team_core.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.client.gui.widget.button.Button.ITooltip;

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
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		final Minecraft minecraft = Minecraft.getInstance();
		
		minecraft.getTextureManager().bind(TEXTURE);
		
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
			final FontRenderer fontRenderer = minecraft.font;
			
			final ITextComponent message = getCurrentText();
			if (message != StringTextComponent.EMPTY) {
				final float xStart;
				final float yStart = y + (height - 8) / 2;
				
				if (leftSideText) {
					xStart = x - (fontRenderer.width(message) + 4);
				} else {
					xStart = x + width + 4;
				}
				
				final int color = getCurrentTextColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB();
				
				if (dropShadow) {
					fontRenderer.drawShadow(matrixStack, getCurrentText(), xStart, yStart, color);
				} else {
					fontRenderer.draw(matrixStack, getCurrentText(), xStart, yStart, color);
				}
			}
		}
	}
}
