package info.u_team.u_team_core.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

/**
 * Utility methods for rendering
 *
 * @author HyCraftHD
 */
public class RenderUtil {
	
	public static final RGBA DARK_CONTAINER_BORDER_COLOR = new RGBA(0x373737FF);
	public static final RGBA MEDIUM_CONTAINER_BORDER_COLOR = new RGBA(0x8B8B8BFF);
	public static final RGBA BRIGHT_CONTAINER_BORDER_COLOR = RGBA.WHITE;
	
	/**
	 * Draws the default container border
	 *
	 * @param poseStack Pose stack
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param width Width
	 * @param height Height
	 * @param blitOffset zLevel for drawing
	 * @param color The shader color. If using {@link RGBA#WHITE} then the drawing will not be colored
	 */
	public static void drawContainerBorder(PoseStack poseStack, int x, int y, int width, int height, float blitOffset, RGBA color) {
		final var tessellator = Tesselator.getInstance();
		final var bufferBuilder = tessellator.getBuilder();
		
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		setShaderColor(color);
		
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.disableTexture();
		
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		
		addColoredQuad(bufferBuilder, poseStack, x, x + width - 1, y, y + 1, DARK_CONTAINER_BORDER_COLOR, blitOffset);
		addColoredQuad(bufferBuilder, poseStack, x, x + 1, y, y + height - 1, DARK_CONTAINER_BORDER_COLOR, blitOffset);
		
		addColoredQuad(bufferBuilder, poseStack, x + width - 1, x + width, y, y + 1, MEDIUM_CONTAINER_BORDER_COLOR, blitOffset);
		addColoredQuad(bufferBuilder, poseStack, x, x + 1, y + height - 1, y + height, MEDIUM_CONTAINER_BORDER_COLOR, blitOffset);
		
		addColoredQuad(bufferBuilder, poseStack, x + 1, x + width - 1, y + height - 1, y + height, BRIGHT_CONTAINER_BORDER_COLOR, blitOffset);
		addColoredQuad(bufferBuilder, poseStack, x + width - 1, x + width, y + 1, y + height, BRIGHT_CONTAINER_BORDER_COLOR, blitOffset);
		
		tessellator.end();
		
		RenderSystem.disableBlend();
		RenderSystem.enableTexture();
	}
	
	/**
	 * Draws a textured box of any size (smallest size is borderSize * 2 square) based on a fixed size textured box with
	 * continuous borders and filler.
	 *
	 * @param poseStack Pose stack
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param u U coordinate
	 * @param v V coordinate
	 * @param width Width
	 * @param height Height
	 * @param textureWidth Texture width
	 * @param textureHeight Texture height
	 * @param topBorder Top border
	 * @param bottomBorder Bottom border
	 * @param leftBorder Left border
	 * @param rightBorder Right border
	 * @param blitOffset zLevel for drawing
	 * @param texture Texture location
	 * @param color The shader color. If using {@link RGBA#WHITE} then the image will not be colored
	 */
	public static void drawContinuousTexturedBox(PoseStack poseStack, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int topBorder, int bottomBorder, int leftBorder, int rightBorder, float blitOffset, ResourceLocation texture, RGBA color) {
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
		
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, texture);
		setShaderColor(color);
		
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		
		// Draw Border
		// Top Left
		addTexturedRect(bufferBuilder, poseStack, x, y, u, v, uScale, vScale, leftBorder, topBorder, blitOffset);
		// Top Right
		addTexturedRect(bufferBuilder, poseStack, x + leftBorder + canvasWidth, y, u + leftBorder + fillerWidth, v, uScale, vScale, rightBorder, topBorder, blitOffset);
		// Bottom Left
		addTexturedRect(bufferBuilder, poseStack, x, y + topBorder + canvasHeight, u, v + topBorder + fillerHeight, uScale, vScale, leftBorder, bottomBorder, blitOffset);
		// Bottom Right
		addTexturedRect(bufferBuilder, poseStack, x + leftBorder + canvasWidth, y + topBorder + canvasHeight, u + leftBorder + fillerWidth, v + topBorder + fillerHeight, uScale, vScale, rightBorder, bottomBorder, blitOffset);
		
		for (var index = 0; index < xPasses + (remainderWidth > 0 ? 1 : 0); index++) {
			// Top Border
			addTexturedRect(bufferBuilder, poseStack, x + leftBorder + (index * fillerWidth), y, u + leftBorder, v, uScale, vScale, (index == xPasses ? remainderWidth : fillerWidth), topBorder, blitOffset);
			// Bottom Border
			addTexturedRect(bufferBuilder, poseStack, x + leftBorder + (index * fillerWidth), y + topBorder + canvasHeight, u + leftBorder, v + topBorder + fillerHeight, uScale, vScale, (index == xPasses ? remainderWidth : fillerWidth), bottomBorder, blitOffset);
			
			// Throw in some filler for good measure
			for (var j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++) {
				addTexturedRect(bufferBuilder, poseStack, x + leftBorder + (index * fillerWidth), y + topBorder + (j * fillerHeight), u + leftBorder, v + topBorder, uScale, vScale, (index == xPasses ? remainderWidth : fillerWidth), (j == yPasses ? remainderHeight : fillerHeight), blitOffset);
			}
		}
		
		// Side Borders
		for (var index = 0; index < yPasses + (remainderHeight > 0 ? 1 : 0); index++) {
			// Left Border
			addTexturedRect(bufferBuilder, poseStack, x, y + topBorder + (index * fillerHeight), u, v + topBorder, uScale, vScale, leftBorder, (index == yPasses ? remainderHeight : fillerHeight), blitOffset);
			// Right Border
			addTexturedRect(bufferBuilder, poseStack, x + leftBorder + canvasWidth, y + topBorder + (index * fillerHeight), u + leftBorder + fillerWidth, v + topBorder, uScale, vScale, rightBorder, (index == yPasses ? remainderHeight : fillerHeight), blitOffset);
		}
		
		tessellator.end();
		
		RenderSystem.disableBlend();
	}
	
	/**
	 * Draws a textured quad.
	 *
	 * @param poseStack Pose stack
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param width Width
	 * @param height Height
	 * @param uWidth U Width
	 * @param vHeight V Height
	 * @param uOffset U Offset
	 * @param vOffset V Offset
	 * @param textureWidth Texture width
	 * @param textureHeight Texture height
	 * @param blitOffset zLevel for drawing
	 * @param texture Texture location
	 * @param color The shader color. If using {@link RGBA#WHITE} then the image will not be colored
	 */
	public static void drawTexturedQuad(PoseStack poseStack, int x, int y, int width, int height, int uWidth, int vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight, float blitOffset, ResourceLocation texture, RGBA color) {
		drawTexturedQuad(poseStack, x, x + width, y, y + height, uOffset / textureWidth, (uOffset + uWidth) / textureWidth, vOffset / textureHeight, (vOffset + vHeight) / textureHeight, blitOffset, texture, color);
	}
	
	/**
	 * Draws a textured quad.
	 *
	 * @param poseStack Pose stack
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param width Width
	 * @param height Height
	 * @param blitOffset zLevel for drawing
	 * @param sprite Texture sprite from texture atlas
	 * @param color The shader color. If using {@link RGBA#WHITE} then the image will not be colored
	 */
	public static void drawTexturedQuad(PoseStack poseStack, int x, int y, int width, int height, float blitOffset, TextureAtlasSprite sprite, RGBA color) {
		drawTexturedQuad(poseStack, x, x + width, y, y + height, sprite.getU0(), sprite.getU1(), sprite.getV0(), sprite.getV1(), blitOffset, sprite.atlas().location(), color);
	}
	
	/**
	 * Draws a textured quad.
	 *
	 * @param poseStack Pose stack
	 * @param x1 X1 coordinate
	 * @param x2 X2 coordinate
	 * @param y1 Y1 coordinate
	 * @param y2 Y2 coordinate
	 * @param u1 U1 coordinate
	 * @param u2 U2 coordinate
	 * @param v1 V1 coordinate
	 * @param v2 V2 coordinate
	 * @param blitOffset zLevel for drawing
	 * @param texture Texture location
	 * @param color The shader color. If using {@link RGBA#WHITE} then the image will not be colored
	 */
	public static void drawTexturedQuad(PoseStack poseStack, int x1, int x2, int y1, int y2, float u1, float u2, float v1, float v2, float blitOffset, ResourceLocation texture, RGBA color) {
		final var tessellator = Tesselator.getInstance();
		final var bufferBuilder = tessellator.getBuilder();
		
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, texture);
		setShaderColor(color);
		
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		
		addTexturedQuad(bufferBuilder, poseStack, x1, x2, y1, y2, u1, u2, v1, v2, blitOffset);
		
		tessellator.end();
		
		RenderSystem.disableBlend();
	}
	
	/**
	 * Adds a textured rectangle to the buffer builder. The vertex format must be {@link DefaultVertexFormat#POSITION_TEX}
	 * and the draw format must be {@link VertexFormat.Mode#QUADS}
	 *
	 * @param bufferBuilder Buffer builder
	 * @param poseStack Pose stack
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param u U coordinate
	 * @param v V coordinate
	 * @param uScale U scale
	 * @param vScale V scale
	 * @param width Width
	 * @param height Height
	 * @param blitOffset zLevel for drawing
	 */
	public static void addTexturedRect(BufferBuilder bufferBuilder, PoseStack poseStack, int x, int y, int u, int v, float uScale, float vScale, int width, int height, float blitOffset) {
		final var matrix = poseStack.last().pose();
		
		bufferBuilder.vertex(matrix, x, y + height, blitOffset).uv(u * uScale, ((v + height) * vScale)).endVertex();
		bufferBuilder.vertex(matrix, x + width, y + height, blitOffset).uv((u + width) * uScale, ((v + height) * vScale)).endVertex();
		bufferBuilder.vertex(matrix, x + width, y, blitOffset).uv((u + width) * uScale, (v * vScale)).endVertex();
		bufferBuilder.vertex(matrix, x, y, blitOffset).uv(u * uScale, (v * vScale)).endVertex();
	}
	
	/**
	 * Adds a textured quad to the buffer builder. The vertex format must be {@link DefaultVertexFormat#POSITION_TEX} and
	 * the draw format must be {@link VertexFormat.Mode#QUADS}
	 *
	 * @param bufferBuilder Buffer builder
	 * @param poseStack Pose stack
	 * @param x1 X1 coordinate
	 * @param x2 X2 coordinate
	 * @param y1 Y1 coordinate
	 * @param y2 Y2 coordinate
	 * @param u1 U1 coordinate
	 * @param u2 U2 coordinate
	 * @param v1 V1 coordinate
	 * @param v2 V2 coordinate
	 * @param blitOffset zLevel for drawing
	 */
	public static void addTexturedQuad(BufferBuilder bufferBuilder, PoseStack poseStack, int x1, int x2, int y1, int y2, float u1, float u2, float v1, float v2, float blitOffset) {
		final var matrix = poseStack.last().pose();
		
		bufferBuilder.vertex(matrix, x1, y2, blitOffset).uv(u1, v2).endVertex();
		bufferBuilder.vertex(matrix, x2, y2, blitOffset).uv(u2, v2).endVertex();
		bufferBuilder.vertex(matrix, x2, y1, blitOffset).uv(u2, v1).endVertex();
		bufferBuilder.vertex(matrix, x1, y1, blitOffset).uv(u1, v1).endVertex();
	}
	
	/**
	 * Adds a quad to the buffer builder. The vertex format must be {@link DefaultVertexFormat#POSITION_COLOR} and the draw
	 * format must be {@link VertexFormat.Mode#QUADS}
	 *
	 * @param bufferBuilder Buffer builder
	 * @param poseStack Pose stack
	 * @param x1 X1 coordinate
	 * @param x2 X2 coordinate
	 * @param y1 Y1 coordinate
	 * @param y2 Y2 coordinate
	 * @param color Color of the vertices
	 * @param blitOffset zLevel for drawing
	 */
	public static void addColoredQuad(BufferBuilder bufferBuilder, PoseStack poseStack, int x1, int x2, int y1, int y2, RGBA color, float blitOffset) {
		final var matrix = poseStack.last().pose();
		
		bufferBuilder.vertex(matrix, x1, y2, blitOffset).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
		bufferBuilder.vertex(matrix, x2, y2, blitOffset).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
		bufferBuilder.vertex(matrix, x2, y1, blitOffset).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
		bufferBuilder.vertex(matrix, x1, y1, blitOffset).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	}
	
	/**
	 * Adds a quad to the buffer builder. The vertex format must be {@link DefaultVertexFormat#POSITION} and the draw format
	 * must be {@link VertexFormat.Mode#QUADS}
	 *
	 * @param bufferBuilder Buffer builder
	 * @param poseStack Pose stack
	 * @param x1 X1 coordinate
	 * @param x2 X2 coordinate
	 * @param y1 Y1 coordinate
	 * @param y2 Y2 coordinate
	 * @param blitOffset zLevel for drawing
	 */
	public static void addQuad(BufferBuilder bufferBuilder, PoseStack poseStack, int x1, int x2, int y1, int y2, float blitOffset) {
		final var matrix = poseStack.last().pose();
		
		bufferBuilder.vertex(matrix, x1, y2, blitOffset).endVertex();
		bufferBuilder.vertex(matrix, x2, y2, blitOffset).endVertex();
		bufferBuilder.vertex(matrix, x2, y1, blitOffset).endVertex();
		bufferBuilder.vertex(matrix, x1, y1, blitOffset).endVertex();
	}
	
	/**
	 * Sets the shader color from {@link RGBA} type
	 *
	 * @param rgba Color
	 */
	public static void setShaderColor(RGBA rgba) {
		RenderSystem.setShaderColor(rgba.getRedComponent(), rgba.getGreenComponent(), rgba.getBlueComponent(), rgba.getAlphaComponent());
	}
	
	/**
	 * Extended matrix that adds getters and setters for all matrix values
	 *
	 * @author HyCraftHD
	 */
	public static class Matrix4fExtended extends Matrix4f {
		
		/**
		 * Creates a matrix4f with the values of the parameter
		 *
		 * @param matrix4f Matrix4f to copy values
		 */
		public Matrix4fExtended(Matrix4f matrix4f) {
			super(matrix4f);
		}
		
		public float getM00() {
			return m00;
		}
		
		public void setM00(float m00) {
			this.m00 = m00;
		}
		
		public float getM01() {
			return m01;
		}
		
		public void setM01(float m01) {
			this.m01 = m01;
		}
		
		public float getM02() {
			return m02;
		}
		
		public void setM02(float m02) {
			this.m02 = m02;
		}
		
		public float getM03() {
			return m03;
		}
		
		public void setM03(float m03) {
			this.m03 = m03;
		}
		
		public float getM10() {
			return m10;
		}
		
		public void setM10(float m10) {
			this.m10 = m10;
		}
		
		public float getM11() {
			return m11;
		}
		
		public void setM11(float m11) {
			this.m11 = m11;
		}
		
		public float getM12() {
			return m12;
		}
		
		public void setM12(float m12) {
			this.m12 = m12;
		}
		
		public float getM13() {
			return m13;
		}
		
		public void setM13(float m13) {
			this.m13 = m13;
		}
		
		public float getM20() {
			return m20;
		}
		
		public void setM20(float m20) {
			this.m20 = m20;
		}
		
		public float getM21() {
			return m21;
		}
		
		public void setM21(float m21) {
			this.m21 = m21;
		}
		
		public float getM22() {
			return m22;
		}
		
		public void setM22(float m22) {
			this.m22 = m22;
		}
		
		public float getM23() {
			return m23;
		}
		
		public void setM23(float m23) {
			this.m23 = m23;
		}
		
		public float getM30() {
			return m30;
		}
		
		public void setM30(float m30) {
			this.m30 = m30;
		}
		
		public float getM31() {
			return m31;
		}
		
		public void setM31(float m31) {
			this.m31 = m31;
		}
		
		public float getM32() {
			return m32;
		}
		
		public void setM32(float m32) {
			this.m32 = m32;
		}
		
		public float getM33() {
			return m33;
		}
		
		public void setM33(float m33) {
			this.m33 = m33;
		}
	}
}
