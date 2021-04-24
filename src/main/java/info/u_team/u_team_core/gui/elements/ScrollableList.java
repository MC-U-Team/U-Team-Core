package info.u_team.u_team_core.gui.elements;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;

import info.u_team.u_team_core.util.RenderUtil;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;

public abstract class ScrollableList<T extends AbstractList.AbstractListEntry<T>> extends ExtendedList<T> {
	
	protected int sideDistance;
	
	protected boolean shouldUseScissor;
	protected boolean shouldRenderTransparentBorder;
	protected float transparentBorderSize;
	
	public ScrollableList(int x, int y, int width, int height, int slotHeight, int sideDistance) {
		super(Minecraft.getInstance(), 0, 0, 0, 0, slotHeight);
		updateSettings(x, y, width, height);
		this.sideDistance = sideDistance;
		transparentBorderSize = 4;
	}
	
	public ScrollableList(int width, int height, int top, int bottom, int left, int right, int slotHeight, int sideDistance) {
		super(Minecraft.getInstance(), 0, 0, 0, 0, slotHeight);
		updateSettings(width, height, top, bottom, left, right);
		this.sideDistance = sideDistance;
		transparentBorderSize = 4;
	}
	
	public void updateSettings(int x, int y, int width, int height) {
		updateSettings(width, minecraft.getMainWindow().getScaledHeight(), y, y + height, x, x + width);
	}
	
	public void updateSettings(int width, int height, int top, int bottom, int left, int right) {
		this.width = width;
		this.height = height;
		this.y0 = top;
		this.y1 = bottom;
		this.x0 = left;
		this.x1 = right;
	}
	
	public int getSideDistance() {
		return sideDistance;
	}
	
	public void setSideDistance(int sideDistance) {
		this.sideDistance = sideDistance;
	}
	
	public boolean isShouldUseScissor() {
		return shouldUseScissor;
	}
	
	public void setShouldUseScissor(boolean shouldUseScissor) {
		this.shouldUseScissor = shouldUseScissor;
	}
	
	public boolean isShouldRenderTransparentBorder() {
		return shouldRenderTransparentBorder;
	}
	
	public void setShouldRenderTransparentBorder(boolean shouldRenderTransparentBorder) {
		this.shouldRenderTransparentBorder = shouldRenderTransparentBorder;
	}
	
	public float getTransparentBorderSize() {
		return transparentBorderSize;
	}
	
	public void setTransparentBorderSize(float transparentBorderSize) {
		this.transparentBorderSize = transparentBorderSize;
	}
	
	@Override
	public int getRowWidth() {
		return width - sideDistance;
	}
	
	@Override
	protected int getScrollbarPosition() {
		return x0 + width - 5;
	}
	
	@Override
	protected void renderList(MatrixStack matrixStack, int rowLeft, int scrollAmount, int mouseX, int mouseY, float partialTicks) {
		if (shouldUseScissor) {
			final MainWindow window = minecraft.getMainWindow();
			final double scaleFactor = window.getGuiScaleFactor();
			
			final int nativeX = MathHelper.ceil(x0 * scaleFactor);
			final int nativeY = MathHelper.ceil(y0 * scaleFactor);
			
			final int nativeWidth = MathHelper.ceil((x1 - x0) * scaleFactor);
			final int nativeHeight = MathHelper.ceil((y1 - y0) * scaleFactor);
			
			RenderUtil.enableScissor(nativeX, window.getHeight() - (nativeY + nativeHeight), nativeWidth, nativeHeight);
			
			// Uncomment to test scissor
			// matrixStack.push();
			// matrixStack.getLast().getMatrix().setIdentity();
			// AbstractGui.fill(matrixStack, 0, 0, window.getScaledWidth(), window.getScaledHeight(), 0x8F00FF00);
			// matrixStack.pop();
			
			super.renderList(matrixStack, rowLeft, scrollAmount, mouseX, mouseY, partialTicks);
			RenderUtil.disableScissor();
		} else {
			super.renderList(matrixStack, rowLeft, scrollAmount, mouseX, mouseY, partialTicks);
		}
		
		if (shouldRenderTransparentBorder) {
			final Tessellator tessellator = Tessellator.getInstance();
			final BufferBuilder buffer = tessellator.getBuffer();
			
			RenderUtil.enableBlend();
			RenderUtil.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ZERO, DestFactor.ONE);
			RenderUtil.shadeModel(GL11.GL_SMOOTH);
			RenderUtil.disableTexture();
			
			buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
			buffer.pos(x0, y0 + transparentBorderSize, 0).color(0, 0, 0, 0).endVertex();
			buffer.pos(x1, y0 + transparentBorderSize, 0).color(0, 0, 0, 0).endVertex();
			buffer.pos(x1, y0, 0).color(0, 0, 0, 255).endVertex();
			buffer.pos(x0, y0, 0).color(0, 0, 0, 255).endVertex();
			buffer.pos(x0, y1, 0).color(0, 0, 0, 255).endVertex();
			buffer.pos(x1, y1, 0).color(0, 0, 0, 255).endVertex();
			buffer.pos(x1, y1 - transparentBorderSize, 0).color(0, 0, 0, 0).endVertex();
			buffer.pos(x0, y1 - transparentBorderSize, 0).color(0, 0, 0, 0).endVertex();
			
			tessellator.draw();
			
			RenderUtil.enableTexture();
			RenderUtil.disableBlend();
		}
	}
	
}
