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
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class WidgetUtil {
	
	private static final String ELLIPSIS = "...";
	
	public static <T extends AbstractWidget & WidgetRenderable & BackgroundColorProvider> void renderWidget(T widget, PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		RenderSystem.enableDepthTest();
		
		widget.renderWidgetTexture(poseStack, mouseX, mouseY, partialTicks);
		widget.renderBackground(poseStack, mouseX, mouseY, partialTicks);
		widget.renderForeground(poseStack, mouseX, mouseY, partialTicks);
		if (widget instanceof TooltipRenderable tooltipRenderable) {
			renderCustomTooltipForWidget(tooltipRenderable, poseStack, mouseX, mouseY, partialTicks);
		}
	}
	
	public static <T extends AbstractWidget & BackgroundColorProvider> void renderButtonLikeTexture(T widget, TextureProvider textureProvider, PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		final RGBA color = respectWidgetAlpha(widget, widget.getCurrentBackgroundColor(poseStack, mouseY, mouseY, partialTicks));
		RenderUtil.drawContinuousTexturedBox(poseStack, widget.getX(), widget.getY(), textureProvider.getU(), textureProvider.getV(), widget.getWidth(), widget.getHeight(), textureProvider.getWidth(), textureProvider.getHeight(), 2, 3, 2, 2, 0, textureProvider.getTexture(), color);
	}
	
	public static <T extends AbstractWidget & TextProvider> void renderText(T widget, PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		final Font font = widget.getCurrentTextFont();
		final TextRenderType renderType = widget.getCurrentTextRenderType();
		final Component message = widget.getCurrentText();
		final RGBA color = respectWidgetAlpha(widget, widget.getCurrentTextColor(poseStack, mouseX, mouseY, partialTicks));
		final float scale;
		if (widget instanceof ScaleProvider scaleProvider) {
			scale = scaleProvider.getCurrentScale(poseStack, mouseY, mouseY, partialTicks);
		} else {
			scale = 1;
		}
		
		if (message == CommonComponents.EMPTY) {
			return;
		}
		
		poseStack.pushPose();
		poseStack.scale(scale, scale, 0);
		
		if (renderType == TextRenderType.ELLIPSIS) {
			renderTextWithCutoff(widget, font, message, color, scale, poseStack, mouseX, mouseY, partialTicks);
		} else if (renderType == TextRenderType.SCROLLING) {
			renderTextWithScrolling(widget, font, message, color, scale, poseStack, mouseX, mouseY, partialTicks);
		}
		
		poseStack.popPose();
	}
	
	private static void renderTextWithScrolling(AbstractWidget widget, Font font, Component message, RGBA color, float scale, PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		final float positionFactor = 1 / scale;
		
		final int maxWidth = widget.getWidth() - 6;
		final int messageWidth = Mth.ceil(scale * font.width(message));
		
		final float yStart = (widget.getY() + (Mth.ceil(widget.getHeight() - 9 * scale)) / 2 + 1) * positionFactor;
		
		if (messageWidth > maxWidth) {
			final int difference = messageWidth - maxWidth;
			
			// Copied from vanilla
			final double d0 = Util.getMillis() / 1000D;
			final double d1 = Math.max((double) difference * 0.5D, 3.0D);
			final double d2 = Math.sin((Math.PI / 2D) * Math.cos((Math.PI * 2D) * d0 / d1)) / 2.0D + 0.5D;
			final double d3 = Mth.lerp(d2, 0.0D, (double) difference);
			
			final float xStart = Mth.floor(widget.getX() - d3);
			
			GuiComponent.enableScissor(widget.getX() + 3, widget.getY(), widget.getX() + widget.getWidth() - 3, widget.getY() + widget.getHeight());
			font.drawShadow(poseStack, message, xStart, yStart, color.getColorARGB());
			GuiComponent.disableScissor();
		} else {
			final float xStart = (widget.getX() + (widget.getWidth() / 2) - messageWidth / 2) * positionFactor;
			font.drawShadow(poseStack, message, xStart, yStart, color.getColorARGB());
		}
	}
	
	private static void renderTextWithCutoff(AbstractWidget widget, Font font, Component message, RGBA color, float scale, PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
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
		
		font.drawShadow(poseStack, message, xStart, yStart, color.getColorARGB());
	}
	
	public static RGBA respectWidgetAlpha(AbstractWidget widget, RGBA color) {
		return color.setAlphaComponent(color.getAlphaComponent() * Mth.clamp(widget.alpha, 0, 1));
	}
	
	public static void renderCustomTooltipForWidget(TooltipRenderable renderable, PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		poseStack.pushPose();
		poseStack.translate(0, 0, 400);
		renderable.renderTooltip(poseStack, mouseX, mouseY, partialTicks);
		poseStack.popPose();
	}
}
