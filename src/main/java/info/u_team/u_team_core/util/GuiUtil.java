package info.u_team.u_team_core.util;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.fml.client.gui.GuiUtils;

/**
 * Some utility methods for guis
 * 
 * @author HyCraftHD
 */
public class GuiUtil {
	
	/**
	 * Draws the default container border
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void drawContainerBorder(MatrixStack matrixStack, int x, int y, int width, int height) {
		// ARGB ... Strange formal
		
		final int darkColor = 0xFF373737;
		final int mediumColor = 0xFF8B8B8B;
		final int brightColor = 0xFFFFFFFF;
		
		AbstractGui.fill(matrixStack, x, y, x + width - 1, y + 1, darkColor);
		AbstractGui.fill(matrixStack, x, y, x + 1, y + height - 1, darkColor);
		
		AbstractGui.fill(matrixStack, x + width - 1, y, x + width, y + 1, mediumColor);
		AbstractGui.fill(matrixStack, x, y + height - 1, x + 1, y + height, mediumColor);
		
		AbstractGui.fill(matrixStack, x + 1, y + height, x + width - 1, y + height - 1, brightColor);
		AbstractGui.fill(matrixStack, x + width - 1, y + 1, x + width, y + height, brightColor);
	}
	
	@SuppressWarnings("deprecation")
	public static void clearColor() {
		RenderSystem.color4f(1, 1, 1, 1);
	}
	
	/**
	 * Draws a textured box of any size (smallest size is borderSize * 2 square) based on a fixed size textured box with
	 * continuous borders and filler. It is assumed that the desired texture ResourceLocation object has been bound using
	 * Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation). <br>
	 * The code is adapted from
	 * {@link GuiUtils#drawContinuousTexturedBox(MatrixStack, int, int, int, int, int, int, int, int, int, int, int, int, float)}
	 * and changed to be more performant and add a color parameter to color the texture
	 *
	 * @param matrixStack The gui matrix stack
	 * @param location Location of the image
	 * @param x X axis offset
	 * @param y Y axis offset
	 * @param u Bound resource location image x offset
	 * @param v Bound resource location image y offset
	 * @param width The desired box width
	 * @param height The desired box height
	 * @param textureWidth The width of the box texture in the resource location image
	 * @param textureHeight The height of the box texture in the resource location image
	 * @param topBorder The size of the box's top border
	 * @param bottomBorder The size of the box's bottom border
	 * @param leftBorder The size of the box's left border
	 * @param rightBorder The size of the box's right border
	 * @param zLevel The zLevel to draw at
	 * @param color The color of the drawing. If using {@link RGBA#WHITE} then the image will not be colored
	 */
	public static void drawContinuousTexturedBox(MatrixStack matrixStack, ResourceLocation location, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int topBorder, int bottomBorder, int leftBorder, int rightBorder, float zLevel, RGBA color) {
		Minecraft.getInstance().getTextureManager().bindTexture(location);
		drawContinuousTexturedBox(matrixStack, x, y, u, v, width, height, textureWidth, textureHeight, topBorder, bottomBorder, leftBorder, rightBorder, zLevel, color);
	}
	
	/**
	 * Draws a textured box of any size (smallest size is borderSize * 2 square) based on a fixed size textured box with
	 * continuous borders and filler. It is assumed that the desired texture ResourceLocation object has been bound using
	 * Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation). <br>
	 * The code is adapted from
	 * {@link GuiUtils#drawContinuousTexturedBox(MatrixStack, int, int, int, int, int, int, int, int, int, int, int, int, float)}
	 * and changed to be more performant and add a color parameter to color the texture
	 *
	 * @param matrixStack The gui matrix stack
	 * @param x X axis offset
	 * @param y Y axis offset
	 * @param u Bound resource location image x offset
	 * @param v Bound resource location image y offset
	 * @param width The desired box width
	 * @param height The desired box height
	 * @param textureWidth The width of the box texture in the resource location image
	 * @param textureHeight The height of the box texture in the resource location image
	 * @param topBorder The size of the box's top border
	 * @param bottomBorder The size of the box's bottom border
	 * @param leftBorder The size of the box's left border
	 * @param rightBorder The size of the box's right border
	 * @param zLevel The zLevel to draw at
	 * @param color The color of the drawing. If using {@link RGBA#WHITE} then the image will not be colored
	 */
	public static void drawContinuousTexturedBox(MatrixStack matrixStack, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int topBorder, int bottomBorder, int leftBorder, int rightBorder, float zLevel, RGBA color) {
		final int fillerWidth = textureWidth - leftBorder - rightBorder;
		final int fillerHeight = textureHeight - topBorder - bottomBorder;
		final int canvasWidth = width - leftBorder - rightBorder;
		final int canvasHeight = height - topBorder - bottomBorder;
		final int xPasses = canvasWidth / fillerWidth;
		final int remainderWidth = canvasWidth % fillerWidth;
		final int yPasses = canvasHeight / fillerHeight;
		final int remainderHeight = canvasHeight % fillerHeight;
		
		final float uScale = 1f / 256;
		final float vScale = 1f / 256;
		
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder bufferBuilder = tessellator.getBuffer();
		
		bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX);
		
		// Draw Border
		// Top Left
		addTexturedColoredRect(bufferBuilder, matrixStack, x, y, u, v, uScale, vScale, leftBorder, topBorder, zLevel, color);
		// Top Right
		addTexturedColoredRect(bufferBuilder, matrixStack, x + leftBorder + canvasWidth, y, u + leftBorder + fillerWidth, v, uScale, vScale, rightBorder, topBorder, zLevel, color);
		// Bottom Left
		addTexturedColoredRect(bufferBuilder, matrixStack, x, y + topBorder + canvasHeight, u, v + topBorder + fillerHeight, uScale, vScale, leftBorder, bottomBorder, zLevel, color);
		// Bottom Right
		addTexturedColoredRect(bufferBuilder, matrixStack, x + leftBorder + canvasWidth, y + topBorder + canvasHeight, u + leftBorder + fillerWidth, v + topBorder + fillerHeight, uScale, vScale, rightBorder, bottomBorder, zLevel, color);
		
		for (int i = 0; i < xPasses + (remainderWidth > 0 ? 1 : 0); i++) {
			// Top Border
			addTexturedColoredRect(bufferBuilder, matrixStack, x + leftBorder + (i * fillerWidth), y, u + leftBorder, v, uScale, vScale, (i == xPasses ? remainderWidth : fillerWidth), topBorder, zLevel, color);
			// Bottom Border
			addTexturedColoredRect(bufferBuilder, matrixStack, x + leftBorder + (i * fillerWidth), y + topBorder + canvasHeight, u + leftBorder, v + topBorder + fillerHeight, uScale, vScale, (i == xPasses ? remainderWidth : fillerWidth), bottomBorder, zLevel, color);
			
			// Throw in some filler for good measure
			for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++) {
				addTexturedColoredRect(bufferBuilder, matrixStack, x + leftBorder + (i * fillerWidth), y + topBorder + (j * fillerHeight), u + leftBorder, v + topBorder, uScale, vScale, (i == xPasses ? remainderWidth : fillerWidth), (j == yPasses ? remainderHeight : fillerHeight), zLevel, color);
			}
		}
		
		// Side Borders
		for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++) {
			// Left Border
			addTexturedColoredRect(bufferBuilder, matrixStack, x, y + topBorder + (j * fillerHeight), u, v + topBorder, uScale, vScale, leftBorder, (j == yPasses ? remainderHeight : fillerHeight), zLevel, color);
			// Right Border
			addTexturedColoredRect(bufferBuilder, matrixStack, x + leftBorder + canvasWidth, y + topBorder + (j * fillerHeight), u + leftBorder + fillerWidth, v + topBorder, uScale, vScale, rightBorder, (j == yPasses ? remainderHeight : fillerHeight), zLevel, color);
		}
		
		tessellator.draw();
	}
	
	/**
	 * Adds a textured rectangle that can be colored to the buffer builder. The vertex format must be
	 * {@link DefaultVertexFormats#POSITION_COLOR_TEX} and the glMode must be {@link GL11#GL_QUADS}
	 * 
	 * @param bufferBuilder The buffer builder to add the vertices
	 * @param matrixStack The gui matrix stack
	 * @param x X coordinate of the drawing
	 * @param y Y coordinate of the drawing
	 * @param u U coordinate of the image
	 * @param v V coordinate of the image
	 * @param width Width of the drawing
	 * @param height Height of the drawing
	 * @param zLevel zLevel of the drawing
	 * @param color Color of the drawing. If using {@link RGBA#WHITE} then the image will not be colored
	 */
	public static void addTexturedColoredRect(BufferBuilder bufferBuilder, MatrixStack matrixStack, int x, int y, int u, int v, float uScale, float vScale, int width, int height, float zLevel, RGBA color) {
		final Matrix4f matrix = matrixStack.getLast().getMatrix();
		
		bufferBuilder.pos(matrix, x, y + height, zLevel).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).tex(u * uScale, ((v + height) * vScale)).endVertex();
		bufferBuilder.pos(matrix, x + width, y + height, zLevel).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).tex((u + width) * uScale, ((v + height) * vScale)).endVertex();
		bufferBuilder.pos(matrix, x + width, y, zLevel).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).tex((u + width) * uScale, (v * vScale)).endVertex();
		bufferBuilder.pos(matrix, x, y, zLevel).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).tex(u * uScale, (v * vScale)).endVertex();
	}
	
	public static void drawTexturedColoredQuad(MatrixStack matrixStack, int x, int y, int width, int height, int uWidth, int vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight, float zLevel, RGBA color) {
		drawTexturedColoredQuad(matrixStack, x, x + width, y, y + height, uOffset / (float) textureWidth, (uOffset + uWidth) / (float) textureWidth, vOffset / (float) textureHeight, (vOffset + vHeight) / (float) textureHeight, zLevel, color);
	}
	
	/**
	 * Draw a {@link TextureAtlasSprite} that can be colored.
	 * 
	 * @param matrixStack The gui matrix stack
	 * @param x X coordinate of the drawing
	 * @param y Y coordinate of the drawing
	 * @param width Width of the drawing
	 * @param height Height of the drawing
	 * @param sprite The Atlas Sprite to define the uvs
	 * @param zLevel zLevel zLevel of the drawing
	 * @param color Color of the drawing. If using {@link RGBA#WHITE} then the image will not be colored
	 */
	public static void drawTexturedColoredQuad(MatrixStack matrixStack, int x, int y, int width, int height, TextureAtlasSprite sprite, float zLevel, RGBA color) {
		drawTexturedColoredQuad(matrixStack, x, x + width, y, y + height, sprite.getMinU(), sprite.getMaxU(), sprite.getMinV(), sprite.getMaxV(), zLevel, color);
	}
	
	/**
	 * Draw a textured quad that can be colored.
	 * 
	 * @param matrixStack The gui matrix stack
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
	public static void drawTexturedColoredQuad(MatrixStack matrixStack, int x1, int x2, int y1, int y2, float u1, float u2, float v1, float v2, float zLevel, RGBA color) {
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder bufferBuilder = tessellator.getBuffer();
		
		bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX);
		
		addTexturedColoredQuad(bufferBuilder, matrixStack, x1, x2, y1, y2, u1, u2, v1, v2, zLevel, color);
		
		tessellator.draw();
	}
	
	/**
	 * Adds a textured quad that can be colored to the buffer builder. The vertex format must be
	 * {@link DefaultVertexFormats#POSITION_COLOR_TEX} and the glMode must be {@link GL11#GL_QUADS}
	 * 
	 * @param bufferBuilder The buffer builder to add the vertices
	 * @param matrixStack The gui matrix stack
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
	public static void addTexturedColoredQuad(BufferBuilder bufferBuilder, MatrixStack matrixStack, int x1, int x2, int y1, int y2, float u1, float u2, float v1, float v2, float zLevel, RGBA color) {
		final Matrix4f matrix = matrixStack.getLast().getMatrix();
		
		addVertexColor(bufferBuilder.pos(matrix, x1, y2, zLevel), color).tex(u1, v2).endVertex();
		addVertexColor(bufferBuilder.pos(matrix, x2, y2, zLevel), color).tex(u2, v2).endVertex();
		addVertexColor(bufferBuilder.pos(matrix, x2, y1, zLevel), color).tex(u2, v1).endVertex();
		addVertexColor(bufferBuilder.pos(matrix, x1, y1, zLevel), color).tex(u1, v1).endVertex();
	}
	
	/**
	 * Append the color of the RGBA to the current vetex on the vertex builder
	 * 
	 * @param builder The vertex builder
	 * @param color The color to append
	 * @return The vertex builder for chaining
	 */
	public static IVertexBuilder addVertexColor(IVertexBuilder builder, RGBA color) {
		return builder.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
}
