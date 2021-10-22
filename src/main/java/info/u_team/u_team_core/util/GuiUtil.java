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
