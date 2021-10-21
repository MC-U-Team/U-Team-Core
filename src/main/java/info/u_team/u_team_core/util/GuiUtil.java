package info.u_team.u_team_core.util;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

/**
 * Utility methods for guis
 *
 * @author HyCraftHD
 */
// TODO docs are not finished
public class GuiUtil {
	
	/**
	 * Draws the default container border
	 *
	 * @param poseStack Pose stack
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param width Width
	 * @param height Height
	 */
	public static void drawContainerBorder(PoseStack poseStack, int x, int y, int width, int height) {
		// TODO draw everything in batch
		
		// TODO make color variables constants
		final var darkColor = 0xFF373737;
		final var mediumColor = 0xFF8B8B8B;
		final var brightColor = 0xFFFFFFFF;
		
		GuiComponent.fill(poseStack, x, y, x + width - 1, y + 1, darkColor);
		GuiComponent.fill(poseStack, x, y, x + 1, y + height - 1, darkColor);
		
		GuiComponent.fill(poseStack, x + width - 1, y, x + width, y + 1, mediumColor);
		GuiComponent.fill(poseStack, x, y + height - 1, x + 1, y + height, mediumColor);
		
		GuiComponent.fill(poseStack, x + 1, y + height, x + width - 1, y + height - 1, brightColor);
		GuiComponent.fill(poseStack, x + width - 1, y + 1, x + width, y + height, brightColor);
	}
	
	/**
	 * Draws a textured box of any size (smallest size is borderSize * 2 square) based on a fixed size textured box with
	 * continuous borders and filler. It is assumed that the desired texture and shader is set.
	 *
	 * @param poseStack Pose stack
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param u Image x coordinate
	 * @param v Image y coordinate
	 * @param width Width
	 * @param height Height
	 * @param textureWidth Texture width
	 * @param textureHeight Texture height
	 * @param topBorder Top border
	 * @param bottomBorder Bottom border
	 * @param leftBorder Left border
	 * @param rightBorder Right border
	 * @param blitOffset The zLevel to draw at
	 * @param color The color of the drawing. If using {@link RGBA#WHITE} then the image will not be colored
	 */
	public static void drawContinuousTexturedBox(PoseStack poseStack, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int topBorder, int bottomBorder, int leftBorder, int rightBorder, float blitOffset, RGBA color) {
		final var fillerWidth = textureWidth - leftBorder - rightBorder;
		final var fillerHeight = textureHeight - topBorder - bottomBorder;
		final var canvasWidth = width - leftBorder - rightBorder;
		final var canvasHeight = height - topBorder - bottomBorder;
		final var xPasses = canvasWidth / fillerWidth;
		final var remainderWidth = canvasWidth % fillerWidth;
		final var yPasses = canvasHeight / fillerHeight;
		final var remainderHeight = canvasHeight % fillerHeight;
		
		final var uScale = 1f / 256;
		final var vScale = 1f / 256;
		
		final var tessellator = Tesselator.getInstance();
		final var bufferBuilder = tessellator.getBuilder();
		
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
		
		// Draw Border
		// Top Left
		addTexturedColoredRect(bufferBuilder, poseStack, x, y, u, v, uScale, vScale, leftBorder, topBorder, blitOffset, color);
		// Top Right
		addTexturedColoredRect(bufferBuilder, poseStack, x + leftBorder + canvasWidth, y, u + leftBorder + fillerWidth, v, uScale, vScale, rightBorder, topBorder, blitOffset, color);
		// Bottom Left
		addTexturedColoredRect(bufferBuilder, poseStack, x, y + topBorder + canvasHeight, u, v + topBorder + fillerHeight, uScale, vScale, leftBorder, bottomBorder, blitOffset, color);
		// Bottom Right
		addTexturedColoredRect(bufferBuilder, poseStack, x + leftBorder + canvasWidth, y + topBorder + canvasHeight, u + leftBorder + fillerWidth, v + topBorder + fillerHeight, uScale, vScale, rightBorder, bottomBorder, blitOffset, color);
		
		for (var i = 0; i < xPasses + (remainderWidth > 0 ? 1 : 0); i++) {
			// Top Border
			addTexturedColoredRect(bufferBuilder, poseStack, x + leftBorder + (i * fillerWidth), y, u + leftBorder, v, uScale, vScale, (i == xPasses ? remainderWidth : fillerWidth), topBorder, blitOffset, color);
			// Bottom Border
			addTexturedColoredRect(bufferBuilder, poseStack, x + leftBorder + (i * fillerWidth), y + topBorder + canvasHeight, u + leftBorder, v + topBorder + fillerHeight, uScale, vScale, (i == xPasses ? remainderWidth : fillerWidth), bottomBorder, blitOffset, color);
			
			// Throw in some filler for good measure
			for (var j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++) {
				addTexturedColoredRect(bufferBuilder, poseStack, x + leftBorder + (i * fillerWidth), y + topBorder + (j * fillerHeight), u + leftBorder, v + topBorder, uScale, vScale, (i == xPasses ? remainderWidth : fillerWidth), (j == yPasses ? remainderHeight : fillerHeight), blitOffset, color);
			}
		}
		
		// Side Borders
		for (var j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++) {
			// Left Border
			addTexturedColoredRect(bufferBuilder, poseStack, x, y + topBorder + (j * fillerHeight), u, v + topBorder, uScale, vScale, leftBorder, (j == yPasses ? remainderHeight : fillerHeight), blitOffset, color);
			// Right Border
			addTexturedColoredRect(bufferBuilder, poseStack, x + leftBorder + canvasWidth, y + topBorder + (j * fillerHeight), u + leftBorder + fillerWidth, v + topBorder, uScale, vScale, rightBorder, (j == yPasses ? remainderHeight : fillerHeight), blitOffset, color);
		}
		
		tessellator.end();
	}
	
	/**
	 * Draw a textured quad that can be colored.
	 *
	 * @param poseStack The gui matrix stack
	 * @param x X coordinate of the drawing
	 * @param y Y coordinate of the drawing
	 * @param width Width of the drawing
	 * @param height Height of the drawing
	 * @param uWidth Width of the image
	 * @param vHeight Height of the image
	 * @param uOffset Offset u of the image
	 * @param vOffset Offset v of the image
	 * @param textureWidth Width of the whole image texture
	 * @param textureHeight Height of the whole image texture
	 * @param zLevel zLevel zLevel of the drawing
	 * @param color Color of the drawing. If using {@link RGBA#WHITE} then the image will not be colored
	 */
	public static void drawTexturedColoredQuad(PoseStack poseStack, int x, int y, int width, int height, int uWidth, int vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight, float zLevel, RGBA color) {
		drawTexturedColoredQuad(poseStack, x, x + width, y, y + height, uOffset / textureWidth, (uOffset + uWidth) / textureWidth, vOffset / textureHeight, (vOffset + vHeight) / textureHeight, zLevel, color);
	}
	
	/**
	 * Draw a {@link TextureAtlasSprite} that can be colored.
	 *
	 * @param poseStack The gui matrix stack
	 * @param x X coordinate of the drawing
	 * @param y Y coordinate of the drawing
	 * @param width Width of the drawing
	 * @param height Height of the drawing
	 * @param sprite The Atlas Sprite to define the uvs
	 * @param zLevel zLevel zLevel of the drawing
	 * @param color Color of the drawing. If using {@link RGBA#WHITE} then the image will not be colored
	 */
	public static void drawTexturedColoredQuad(PoseStack poseStack, int x, int y, int width, int height, TextureAtlasSprite sprite, float zLevel, RGBA color) {
		drawTexturedColoredQuad(poseStack, x, x + width, y, y + height, sprite.getU0(), sprite.getU1(), sprite.getV0(), sprite.getV1(), zLevel, color);
	}
	
	/**
	 * Draw a textured quad that can be colored.
	 *
	 * @param poseStack The gui matrix stack
	 * @param x1 X1 coordinate of the drawing
	 * @param x2 X2 coordinate of the drawing
	 * @param y1 Y1 coordinate of the drawing
	 * @param y2 Y2 coordinate of the drawing
	 * @param u1 U1 coordinate of the image
	 * @param u2 U2 coordinate of the image
	 * @param v1 V1 coordinate of the image
	 * @param v2 V2 coordinate of the image
	 * @param zLevel zLevel of the drawing
	 * @param color Color of the drawing. If using {@link RGBA#WHITE} then the image will not be colored
	 */
	public static void drawTexturedColoredQuad(PoseStack poseStack, int x1, int x2, int y1, int y2, float u1, float u2, float v1, float v2, float zLevel, RGBA color) {
		final var tessellator = Tesselator.getInstance();
		final var bufferBuilder = tessellator.getBuilder();
		
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
		
		addTexturedColoredQuad(bufferBuilder, poseStack, x1, x2, y1, y2, u1, u2, v1, v2, zLevel, color);
		
		tessellator.end();
	}
	
	/**
	 * Adds a textured quad that can be colored to the buffer builder. The vertex format must be
	 * {@link DefaultVertexFormat#POSITION_COLOR_TEX} and the draw format must be {@link VertexFormat.Mode#QUADS}
	 * 
	 * @param bufferBuilder
	 * @param matrixStack
	 * @param x1
	 * @param x2
	 * @param y1
	 * @param y2
	 * @param u1
	 * @param u2
	 * @param v1
	 * @param v2
	 * @param zLevel
	 * @param color
	 */
	public static void addTexturedColoredQuad(BufferBuilder bufferBuilder, PoseStack matrixStack, int x1, int x2, int y1, int y2, float u1, float u2, float v1, float v2, float zLevel, RGBA color) {
		final var matrix = matrixStack.last().pose();
		
		addVertexColor(bufferBuilder.vertex(matrix, x1, y2, zLevel), color).uv(u1, v2).endVertex();
		addVertexColor(bufferBuilder.vertex(matrix, x2, y2, zLevel), color).uv(u2, v2).endVertex();
		addVertexColor(bufferBuilder.vertex(matrix, x2, y1, zLevel), color).uv(u2, v1).endVertex();
		addVertexColor(bufferBuilder.vertex(matrix, x1, y1, zLevel), color).uv(u1, v1).endVertex();
	}
	
	/**
	 * Adds a textured rectangle that can be colored to the buffer builder. The vertex format must be
	 * {@link DefaultVertexFormat#POSITION_COLOR_TEX} and the draw format must be {@link VertexFormat.Mode#QUADS}
	 * 
	 * @param bufferBuilder Buffer builder
	 * @param poseStack Pose stack
	 * @param x X coordiante
	 * @param y Y coordinate
	 * @param u
	 * @param v
	 * @param uScale
	 * @param vScale
	 * @param width
	 * @param height
	 * @param blitOffset
	 * @param color
	 */
	public static void addTexturedColoredRect(BufferBuilder bufferBuilder, PoseStack poseStack, int x, int y, int u, int v, float uScale, float vScale, int width, int height, float blitOffset, RGBA color) {
		final var matrix = poseStack.last().pose();
		
		bufferBuilder.vertex(matrix, x, y + height, blitOffset).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(u * uScale, ((v + height) * vScale)).endVertex();
		bufferBuilder.vertex(matrix, x + width, y + height, blitOffset).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv((u + width) * uScale, ((v + height) * vScale)).endVertex();
		bufferBuilder.vertex(matrix, x + width, y, blitOffset).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv((u + width) * uScale, (v * vScale)).endVertex();
		bufferBuilder.vertex(matrix, x, y, blitOffset).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(u * uScale, (v * vScale)).endVertex();
	}
	
	/**
	 * Append the color of the RGBA to the current vertex on the vertex builder
	 *
	 * @param builder The vertex builder
	 * @param color The color to append
	 * @return The vertex builder for chaining
	 */
	public static VertexConsumer addVertexColor(VertexConsumer builder, RGBA color) {
		return builder.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
}
