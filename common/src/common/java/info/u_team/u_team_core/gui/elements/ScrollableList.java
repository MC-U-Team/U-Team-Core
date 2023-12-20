package info.u_team.u_team_core.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.renderer.RenderType;

public abstract class ScrollableList<T extends ObjectSelectionList.Entry<T>> extends ObjectSelectionList<T> {
	
	protected int sideDistance;
	
	protected boolean renderTransparentBorder;
	protected int transparentBorderSize;
	
	public ScrollableList(int x, int y, int width, int height, int slotHeight, int sideDistance) {
		super(Minecraft.getInstance(), width, height, y, slotHeight);
		setX(x);
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
	
	public void setTransparentBorderSize(int transparentBorderSize) {
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
		
		if (renderTransparentBorder) {
			guiGraphics.fillGradient(RenderType.guiOverlay(), getX(), getY(), getRight(), getY() + transparentBorderSize, 0xFF000000, 0, 0);
			guiGraphics.fillGradient(RenderType.guiOverlay(), getX(), getBottom() - transparentBorderSize, getRight(), getBottom(), 0, 0xFF000000, 0);
		}
	}
}
