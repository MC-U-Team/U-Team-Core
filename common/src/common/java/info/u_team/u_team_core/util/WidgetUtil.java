package info.u_team.u_team_core.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.BackgroundColorProvider;
import info.u_team.u_team_core.api.gui.ScaleProvider;
import info.u_team.u_team_core.api.gui.TextProvider;
import info.u_team.u_team_core.api.gui.TextSettingsProvider.TextRenderType;
import info.u_team.u_team_core.api.gui.TextureProvider;
import info.u_team.u_team_core.api.gui.TooltipRenderable;
import info.u_team.u_team_core.api.gui.WidgetRenderable;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class WidgetUtil {
	
	private static final String ELLIPSIS = "...";
	
	public static <T extends AbstractWidget & WidgetRenderable & BackgroundColorProvider> void renderWidget(T widget, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		RenderSystem.enableDepthTest();
		
		widget.renderWidgetTexture(guiGraphics, mouseX, mouseY, partialTick);
		widget.renderBehind(guiGraphics, mouseX, mouseY, partialTick);
		widget.renderBefore(guiGraphics, mouseX, mouseY, partialTick);
		if (widget instanceof final TooltipRenderable tooltipRenderable) {
			renderCustomTooltipForWidget(tooltipRenderable, guiGraphics, mouseX, mouseY, partialTick);
		}
	}
	
	public static <T extends AbstractWidget & BackgroundColorProvider> void renderButtonLikeTexture(T widget, TextureProvider textureProvider, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		RenderUtil.setShaderColor(respectWidgetAlpha(widget, widget.getCurrentBackgroundColor(guiGraphics, mouseY, mouseY, partialTick)));
		guiGraphics.blitSprite(textureProvider.getTexture(), widget.getX(), widget.getY(), widget.getWidth(), widget.getHeight());
		RenderUtil.resetShaderColor();
	}
	
	public static <T extends AbstractWidget & TextProvider> void renderText(T widget, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		final Font font = widget.getCurrentTextFont();
		final TextRenderType renderType = widget.getCurrentTextRenderType();
		final Component message = widget.getCurrentText();
		final RGBA color = respectWidgetAlpha(widget, widget.getCurrentTextColor(guiGraphics, mouseX, mouseY, partialTick));
		final float scale;
		if (widget instanceof final ScaleProvider scaleProvider) {
			scale = scaleProvider.getCurrentScale(guiGraphics, mouseY, mouseY, partialTick);
		} else {
			scale = 1;
		}
		
		if (message == CommonComponents.EMPTY) {
			return;
		}
		
		final PoseStack poseStack = guiGraphics.pose();
		poseStack.pushPose();
		poseStack.scale(scale, scale, 0);
		
		if (renderType == TextRenderType.ELLIPSIS) {
			renderTextWithCutoff(widget, font, message, color, scale, guiGraphics, mouseX, mouseY, partialTick);
		} else if (renderType == TextRenderType.SCROLLING) {
			renderTextWithScrolling(widget, font, message, color, scale, guiGraphics, mouseX, mouseY, partialTick);
		}
		
		poseStack.popPose();
	}
	
	private static void renderTextWithScrolling(AbstractWidget widget, Font font, Component message, RGBA color, float scale, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		final float positionFactor = 1 / scale;
		
		final int maxWidth = widget.getWidth() - 6;
		final int messageWidth = Mth.ceil(scale * font.width(message));
		
		final float yStart = (widget.getY() + (Mth.ceil(widget.getHeight() - 9 * scale)) / 2 + 1) * positionFactor;
		
		if (messageWidth > maxWidth) {
			final int difference = messageWidth - maxWidth;
			
			// Copied from vanilla
			final double d0 = Util.getMillis() / 1000D;
			final double d1 = Math.max(difference * 0.5D, 3.0D);
			final double d2 = Math.sin((Math.PI / 2D) * Math.cos((Math.PI * 2D) * d0 / d1)) / 2.0D + 0.5D;
			final double d3 = Mth.lerp(d2, 0.0D, difference);
			
			final float xStart = (widget.getX() + 3 - (int) d3) * positionFactor;
			
			guiGraphics.enableScissor(widget.getX() + 3, widget.getY(), widget.getX() + widget.getWidth() - 3, widget.getY() + widget.getHeight());
			FontUtil.drawString(guiGraphics, font, message, xStart, yStart, color.getColorARGB(), true);
			guiGraphics.disableScissor();
		} else {
			final float xStart = (widget.getX() + (widget.getWidth() / 2) - messageWidth / 2) * positionFactor;
			FontUtil.drawString(guiGraphics, font, message, xStart, yStart, color.getColorARGB(), true);
		}
	}
	
	private static void renderTextWithCutoff(AbstractWidget widget, Font font, Component message, RGBA color, float scale, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		final float positionFactor = 1 / scale;
		
		final int maxWidth = widget.getWidth() - 6;
		int messageWidth = Mth.ceil(scale * font.width(message));
		final int ellipsisWidth = Mth.ceil(scale * font.width(ELLIPSIS));
		
		if (messageWidth > maxWidth && messageWidth > ellipsisWidth) {
			message = Component.literal(font.substrByWidth(message, Mth.floor(maxWidth * positionFactor) - ellipsisWidth).getString() + ELLIPSIS);
			messageWidth = maxWidth;
		}
		
		final float xStart = (widget.getX() + (widget.getWidth() / 2) - messageWidth / 2) * positionFactor;
		final float yStart = (widget.getY() + (Mth.ceil(widget.getHeight() - 9 * scale)) / 2 + 1) * positionFactor;
		
		FontUtil.drawString(guiGraphics, font, message, xStart, yStart, color.getColorARGB(), true);
	}
	
	public static RGBA respectWidgetAlpha(AbstractWidget widget, RGBA color) {
		return color.setAlphaComponent(color.getAlphaComponent() * Mth.clamp(widget.alpha, 0, 1));
	}
	
	public static void renderCustomTooltipForWidget(TooltipRenderable renderable, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		final PoseStack poseStack = guiGraphics.pose();
		poseStack.pushPose();
		poseStack.translate(0, 0, 400);
		renderable.renderTooltip(guiGraphics, mouseX, mouseY, partialTick);
		poseStack.popPose();
	}
}
