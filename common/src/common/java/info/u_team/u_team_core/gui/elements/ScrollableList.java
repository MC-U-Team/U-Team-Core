package info.u_team.u_team_core.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;

public abstract class ScrollableList<T extends ObjectSelectionList.Entry<T>> extends ObjectSelectionList<T> {
	
	protected int sideDistance;
	
	protected boolean renderTransparentBorder;
	protected float transparentBorderSize;
	
	public ScrollableList(int y, int width, int height, int slotHeight, int sideDistance) {
		super(Minecraft.getInstance(), y, width, height, slotHeight);
		this.sideDistance = sideDistance;
		transparentBorderSize = 4;
	}
	
	public int getSideDistance() {
		return sideDistance;
	}
	
	public void setSideDistance(int sideDistance) {
		this.sideDistance = sideDistance;
	}
	
	public boolean isRenderTransparentBorder() {
		return renderTransparentBorder;
	}
	
	public void setRenderTransparentBorder(boolean renderTransparentBorder) {
		this.renderTransparentBorder = renderTransparentBorder;
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
		return x + width - 5;
	}
	
	@Override
	protected void renderList(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.renderList(guiGraphics, mouseX, mouseY, partialTick);
		
		// TODO evaluate if useful
		// if (renderTransparentBorder) {
		// final Tesselator tessellator = Tesselator.getInstance();
		// final BufferBuilder buffer = tessellator.getBuilder();
		//
		// RenderSystem.setShader(GameRenderer::getPositionColorShader);
		// RenderUtil.setShaderColor(RGBA.BLACK);
		//
		// RenderSystem.enableBlend();
		// RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ZERO,
		// DestFactor.ONE);
		//
		// buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		// buffer.vertex(x0, y0 + transparentBorderSize, 0).color(0, 0, 0, 0).endVertex();
		// buffer.vertex(x1, y0 + transparentBorderSize, 0).color(0, 0, 0, 0).endVertex();
		// buffer.vertex(x1, y0, 0).color(0, 0, 0, 255).endVertex();
		// buffer.vertex(x0, y0, 0).color(0, 0, 0, 255).endVertex();
		// buffer.vertex(x0, y1, 0).color(0, 0, 0, 255).endVertex();
		// buffer.vertex(x1, y1, 0).color(0, 0, 0, 255).endVertex();
		// buffer.vertex(x1, y1 - transparentBorderSize, 0).color(0, 0, 0, 0).endVertex();
		// buffer.vertex(x0, y1 - transparentBorderSize, 0).color(0, 0, 0, 0).endVertex();
		//
		// tessellator.end();
		//
		// RenderSystem.disableBlend();
		//
		// RenderUtil.setShaderColor(RGBA.WHITE);
		// }
	}
}
