package info.u_team.u_team_core.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.BackgroundColorProvider;
import info.u_team.u_team_core.api.gui.PerspectiveRenderable;
import info.u_team.u_team_core.api.gui.ScaleProvider;
import info.u_team.u_team_core.api.gui.TextProvider;
import info.u_team.u_team_core.api.gui.TextureProvider;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class WidgetUtil {
	
	public static <T extends AbstractWidget & PerspectiveRenderable & BackgroundColorProvider> void renderButtonLikeWidget(T widget, TextureProvider textureProvider, PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		RenderSystem.enableDepthTest();
		
		final RGBA color = respectWidgetAlpha(widget, widget.getCurrentBackgroundColor(poseStack, mouseY, mouseY, partialTicks));
		RenderUtil.drawContinuousTexturedBox(poseStack, widget.getX(), widget.getY(), textureProvider.getU(), textureProvider.getV(), widget.getWidth(), widget.getHeight(), textureProvider.getWidth(), textureProvider.getHeight(), 2, 3, 2, 2, 0, textureProvider.getTexture(), color);
		
		widget.renderBackground(poseStack, mouseX, mouseY, partialTicks);
		widget.renderForeground(poseStack, mouseX, mouseY, partialTicks);
	}
	
	public static <T extends AbstractWidget> RGBA respectWidgetAlpha(T widget, RGBA color) {
		return color.setAlphaComponent(color.getAlphaComponent() * Mth.clamp(widget.alpha, 0, 1));
	}
	
	public static <T extends AbstractWidget & TextProvider> void renderText(T widget, PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		final Font font = widget.getCurrentTextFont();
		final Component message = widget.getCurrentText();
		final RGBA color = respectWidgetAlpha(widget, widget.getCurrentTextColor(poseStack, mouseX, mouseY, partialTicks));
		
		final int border = 2;
		final int x = widget.getX() + border;
		final int width = widget.getX() + widget.getWidth() - border;
		
		AbstractWidget.renderScrollingString(poseStack, font, message, x, widget.getY(), width, widget.getY() + widget.getHeight(), color.getColorARGB());
	}
	
	public static <T extends AbstractWidget & TextProvider & ScaleProvider> void renderScaledText(T widget, PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		final float scale = widget.getCurrentScale(poseStack, mouseX, mouseY, partialTicks);
		
		if (scale == 1) {
			renderText(widget, poseStack, mouseX, mouseY, partialTicks);
		} else {
			final Font font = widget.getCurrentTextFont();
			
			Component message = widget.getCurrentText();
			if (message != CommonComponents.EMPTY) {
				final int messageWidth = Mth.ceil(scale * font.width(message));
				final int ellipsisWidth = Mth.ceil(scale * font.width("..."));
				
				if (messageWidth > widget.getWidth() - 6 && messageWidth > ellipsisWidth) {
					message = Component.literal(font.substrByWidth(message, widget.getWidth() - 6 - ellipsisWidth).getString() + "...");
				}
				
				final float positionFactor = 1 / scale;
				
				final float xStart = (widget.getX() + (widget.getWidth() / 2) - messageWidth / 2) * positionFactor;
				final float yStart = (widget.getY() + ((int) (widget.getHeight() - 8 * scale)) / 2) * positionFactor;
				
				poseStack.pushPose();
				poseStack.scale(scale, scale, 0);
				font.drawShadow(poseStack, message, xStart, yStart, widget.getCurrentTextColor(poseStack, mouseX, mouseY, partialTicks).getColorARGB());
				poseStack.popPose();
			}
		}
	}
}
