package info.u_team.u_team_core.gui.elements;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.config.GuiUtils;

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
		
		int messageWidth = MathHelper.ceil(scale * fontRenderer.getStringWidth(message));
		int ellipsisWidth = MathHelper.ceil(scale * fontRenderer.getStringWidth("..."));
		
		if (messageWidth > width - 6 && messageWidth > ellipsisWidth) {
			message = fontRenderer.trimStringToWidth(message, width - 6 - ellipsisWidth).trim() + "...";
		}
		
		GL11.glPushMatrix();
		GL11.glTranslatef(x + width / 2, y + (height - 8 * scale) / 2, 0);
		GL11.glScalef(scale, scale, 0);
		drawCenteredString(fontRenderer, message, 0, 0, getFGColor());
		GL11.glPopMatrix();
	}
	
}
