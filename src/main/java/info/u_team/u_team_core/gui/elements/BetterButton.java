package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class BetterButton extends UButton {
	
	protected float scale;
	
	public BetterButton(int x, int y, int width, int height, float scale, ITextComponent display) {
		this(x, y, width, height, scale, display, EMTPY_PRESSABLE);
	}
	
	public BetterButton(int x, int y, int width, int height, float scale, ITextComponent display, IPressable pessable) {
		super(x, y, width, height, display, pessable);
		this.scale = scale;
	}
	
	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		final Minecraft minecraft = Minecraft.getInstance();
		final FontRenderer fontRenderer = minecraft.fontRenderer;
		
		String message = getMessage().getString();
		
		GuiUtils.drawContinuousTexturedBox(WIDGETS_LOCATION, x, y, 0, 46 + getYImage(isHovered()) * 20, width, height, 200, 20, 2, 3, 2, 2, 0);
		renderBg(matrixStack, minecraft, mouseX, mouseY);
		
		final int messageWidth = MathHelper.ceil(scale * fontRenderer.getStringWidth(message));
		final int ellipsisWidth = MathHelper.ceil(scale * fontRenderer.getStringWidth("..."));
		
		if (messageWidth > width - 6 && messageWidth > ellipsisWidth) {
			message = fontRenderer.func_238412_a_(message, width - 6 - ellipsisWidth).trim() + "...";
		}
		
		final float positionFactor = 1 / scale;
		
		final float xStart = (x + (width / 2) - messageWidth / 2) * positionFactor;
		final float yStart = (y + ((int) (height - 8 * scale)) / 2) * positionFactor;
		
		fontRenderer.renderString(message, xStart, yStart, getFGColor(), Matrix4f.makeScale(scale, scale, 0), true, false);
	}
}
