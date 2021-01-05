package info.u_team.u_team_core.util;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.api.gui.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;

public class WidgetUtil {
	
	public static boolean isHovered(Widget widget) {
		return widget.isHovered;
	}
	
	public static <T extends Widget & IPerspectiveRenderable & IBackgroundColorProvider> void renderButtonLikeWidget(T widget, ITextureProvider textureProvider, MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		RenderUtil.enableBlend();
		RenderUtil.defaultBlendFunc();
		GuiUtil.drawContinuousTexturedBox(matrixStack, textureProvider.getTexture(), widget.x, widget.y, textureProvider.getU(), textureProvider.getV(), widget.width, widget.height, textureProvider.getWidth(), textureProvider.getHeight(), 2, 3, 2, 2, widget.getBlitOffset(), widget.getCurrentBackgroundColor(matrixStack, mouseY, mouseY, partialTicks));
		RenderUtil.disableBlend();
		
		final Minecraft minecraft = Minecraft.getInstance();
		
		widget.renderBackground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
		widget.renderForeground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
	}
	
	public static <T extends Widget & ITextProvider> void renderText(T widget, MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		final FontRenderer fontRenderer = minecraft.fontRenderer;
		
		ITextComponent message = widget.getCurrentText();
		if (message != StringTextComponent.EMPTY) {
			final int messageWidth = fontRenderer.getStringPropertyWidth(message);
			final int ellipsisWidth = fontRenderer.getStringWidth("...");
			
			if (messageWidth > widget.width - 6 && messageWidth > ellipsisWidth) {
				message = new StringTextComponent(fontRenderer.func_238417_a_(message, widget.width - 6 - ellipsisWidth).getString() + "...");
			}
			
			final float xStart = (widget.x + (widget.width / 2) - messageWidth / 2);
			final float yStart = (widget.y + ((int) (widget.height - 8)) / 2);
			
			fontRenderer.func_243246_a(matrixStack, message, xStart, yStart, widget.getCurrentTextColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB());
		}
	}
	
	public static <T extends Widget & ITextProvider & IScaleProvider> void renderScaledText(T widget, MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		final float scale = widget.getCurrentScale(matrixStack, mouseX, mouseY, partialTicks);
		
		if (scale == 1) {
			renderText(widget, matrixStack, minecraft, mouseX, mouseY, partialTicks);
		} else {
			final FontRenderer fontRenderer = minecraft.fontRenderer;
			
			ITextComponent message = widget.getCurrentText();
			if (message != StringTextComponent.EMPTY) {
				final int messageWidth = MathHelper.ceil(scale * fontRenderer.getStringPropertyWidth(message));
				final int ellipsisWidth = MathHelper.ceil(scale * fontRenderer.getStringWidth("..."));
				
				if (messageWidth > widget.width - 6 && messageWidth > ellipsisWidth) {
					message = new StringTextComponent(fontRenderer.func_238417_a_(message, widget.width - 6 - ellipsisWidth).getString() + "...");
				}
				
				final float positionFactor = 1 / scale;
				
				final float xStart = (widget.x + (widget.width / 2) - messageWidth / 2) * positionFactor;
				final float yStart = (widget.y + ((int) (widget.height - 8 * scale)) / 2) * positionFactor;
				
				matrixStack.push();
				matrixStack.scale(scale, scale, 0);
				fontRenderer.func_243246_a(matrixStack, message, xStart, yStart, widget.getCurrentTextColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB());
				matrixStack.pop();
			}
		}
	}
}
