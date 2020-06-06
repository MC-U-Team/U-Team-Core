package info.u_team.u_team_core.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.minecraftforge.fml.client.gui.widget.Slider;

public class BetterFontSlider extends Slider {
	
	protected float scale;
	
	public BetterFontSlider(int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr, float scale, ISlider par) {
		super(xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr, slider -> {
		}, par);
		this.scale = scale;
	}
	
	@Override
	protected void renderBg(Minecraft minecraft, int x, int y) {
		if (visible) {
			if (dragging) {
				sliderValue = (x - (this.x + 4)) / (float) (this.width - 8);
				updateSlider();
			}
			GuiUtils.drawContinuousTexturedBox(WIDGETS_LOCATION, this.x + (int) (this.sliderValue * (this.width - 8)), this.y, 0, 66 + (isHovered() ? 20 : 0), 8, this.height, 200, 20, 2, 3, 2, 2, this.getBlitOffset());
		}
	}
	
	@Override
	public void renderButton(int mouseX, int mouseY, float partial) {
		final Minecraft minecraft = Minecraft.getInstance();
		final FontRenderer fontRenderer = minecraft.fontRenderer;
		
		String message = getMessage();
		
		GuiUtils.drawContinuousTexturedBox(WIDGETS_LOCATION, x, y, 0, 46 + getYImage(isHovered()) * 20, width, height, 200, 20, 2, 3, 2, 2, 0);
		renderBg(minecraft, mouseX, mouseY);
		
		final int messageWidth = MathHelper.ceil(scale * fontRenderer.getStringWidth(message));
		final int ellipsisWidth = MathHelper.ceil(scale * fontRenderer.getStringWidth("..."));
		
		if (messageWidth > width - 6 && messageWidth > ellipsisWidth) {
			message = fontRenderer.trimStringToWidth(message, width - 6 - ellipsisWidth).trim() + "...";
		}
		
		final float positionFactor = 1 / scale;
		
		final float xStart = (x + (width / 2) - messageWidth / 2) * positionFactor;
		final float yStart = (y + ((int) (height - 8 * scale)) / 2) * positionFactor;
		
		fontRenderer.renderString(message, xStart, yStart, getFGColor(), Matrix4f.makeScale(scale, scale, 0), true);
	}
}
