package info.u_team.u_team_core.util;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.IBackgroundColorProvider;
import info.u_team.u_team_core.api.gui.IPerspectiveRenderable;
import info.u_team.u_team_core.api.gui.IScaleProvider;
import info.u_team.u_team_core.api.gui.ITextProvider;
import info.u_team.u_team_core.api.gui.ITextureProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;

public class WidgetUtil {
	
	public static boolean isHovered(AbstractWidget widget) {
		return widget.isHovered;
	}
	
	public static <T extends AbstractWidget & IPerspectiveRenderable & IBackgroundColorProvider> void renderButtonLikeWidget(T widget, ITextureProvider textureProvider, PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		RenderUtil.enableBlend();
		RenderUtil.defaultBlendFunc();
		GuiUtil.drawContinuousTexturedBox(matrixStack, textureProvider.getTexture(), widget.x, widget.y, textureProvider.getU(), textureProvider.getV(), widget.width, widget.height, textureProvider.getWidth(), textureProvider.getHeight(), 2, 3, 2, 2, widget.getBlitOffset(), widget.getCurrentBackgroundColor(matrixStack, mouseY, mouseY, partialTicks));
		RenderUtil.disableBlend();
		
		final var minecraft = Minecraft.getInstance();
		
		widget.renderBackground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
		widget.renderForeground(matrixStack, minecraft, mouseX, mouseY, partialTicks);
	}
	
	public static <T extends AbstractWidget & ITextProvider> void renderText(T widget, PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		final var fontRenderer = minecraft.font;
		
		var message = widget.getCurrentText();
		if (message != TextComponent.EMPTY) {
			final var messageWidth = fontRenderer.width(message);
			final var ellipsisWidth = fontRenderer.width("...");
			
			if (messageWidth > widget.width - 6 && messageWidth > ellipsisWidth) {
				message = new TextComponent(fontRenderer.substrByWidth(message, widget.width - 6 - ellipsisWidth).getString() + "...");
			}
			
			final float xStart = (widget.x + (widget.width / 2) - messageWidth / 2);
			final float yStart = (widget.y + (widget.height - 8) / 2);
			
			fontRenderer.drawShadow(matrixStack, message, xStart, yStart, widget.getCurrentTextColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB());
		}
	}
	
	public static <T extends AbstractWidget & ITextProvider & IScaleProvider> void renderScaledText(T widget, PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		final var scale = widget.getCurrentScale(matrixStack, mouseX, mouseY, partialTicks);
		
		if (scale == 1) {
			renderText(widget, matrixStack, minecraft, mouseX, mouseY, partialTicks);
		} else {
			final var fontRenderer = minecraft.font;
			
			var message = widget.getCurrentText();
			if (message != TextComponent.EMPTY) {
				final var messageWidth = Mth.ceil(scale * fontRenderer.width(message));
				final var ellipsisWidth = Mth.ceil(scale * fontRenderer.width("..."));
				
				if (messageWidth > widget.width - 6 && messageWidth > ellipsisWidth) {
					message = new TextComponent(fontRenderer.substrByWidth(message, widget.width - 6 - ellipsisWidth).getString() + "...");
				}
				
				final var positionFactor = 1 / scale;
				
				final var xStart = (widget.x + (widget.width / 2) - messageWidth / 2) * positionFactor;
				final var yStart = (widget.y + ((int) (widget.height - 8 * scale)) / 2) * positionFactor;
				
				matrixStack.pushPose();
				matrixStack.scale(scale, scale, 0);
				fontRenderer.drawShadow(matrixStack, message, xStart, yStart, widget.getCurrentTextColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB());
				matrixStack.popPose();
			}
		}
	}
	
	public static void renderTooltips(List<Widget> widgets, PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		widgets.forEach(widget -> {
			if (widget instanceof IPerspectiveRenderable perspectiveRenderable) {
				perspectiveRenderable.renderToolTip(matrixStack, minecraft, mouseX, mouseY, partialTicks);
			} else if (widget instanceof AbstractWidget abstractWidget) {
				abstractWidget.renderToolTip(matrixStack, mouseX, mouseY);
			}
		});
	}
}
