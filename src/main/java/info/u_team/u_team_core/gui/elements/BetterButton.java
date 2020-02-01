package info.u_team.u_team_core.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class BetterButton extends UButton {
	
	protected float scale;
	
	public BetterButton(int x, int y, int width, int height, float scale, String displayString) {
		this(x, y, width, height, scale, displayString, EMTPY_PRESSABLE);
	}
	
	public BetterButton(int x, int y, int width, int height, float scale, String displayString, IPressable pessable) {
		super(x, y, width, height, displayString, pessable);
		this.scale = scale;
	}
	
	@Override
	public void renderButton(int mouseX, int mouseY, float partialTicks) {
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
