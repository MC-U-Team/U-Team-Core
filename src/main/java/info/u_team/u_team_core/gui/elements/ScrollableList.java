package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.util.RenderUtil;
import net.minecraft.client.*;
import net.minecraft.client.gui.widget.list.*;
import net.minecraft.util.math.MathHelper;

public abstract class ScrollableList<T extends AbstractList.AbstractListEntry<T>> extends ExtendedList<T> {
	
	protected int listWidth;
	protected int scrollbarPos;
	
	protected boolean shouldUseScissor;
	
	public ScrollableList(int x, int y, int width, int height, int slotHeight, int listWidth, int scrollbarPos) {
		super(Minecraft.getInstance(), 0, 0, 0, 0, slotHeight);
		updateSettings(x, y, width, height);
		this.listWidth = listWidth;
		this.scrollbarPos = scrollbarPos;
	}
	
	public ScrollableList(int width, int height, int top, int bottom, int left, int right, int slotHeight, int listWidth, int scrollbarPos) {
		super(Minecraft.getInstance(), 0, 0, 0, 0, slotHeight);
		updateSettings(width, height, top, bottom, left, right);
		this.listWidth = listWidth;
		this.scrollbarPos = scrollbarPos;
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
	
	@Override
	public int getRowWidth() {
		return width - listWidth;
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
			
			// AbstractGui.fill(matrixStack, 0, 0, window.getScaledWidth(), window.getScaledHeight(), 0xFF00FF00); // test scissor
			super.renderList(matrixStack, rowLeft, scrollAmount, mouseX, mouseY, partialTicks);
			RenderUtil.disableScissor();
		} else {
			super.renderList(matrixStack, rowLeft, scrollAmount, mouseX, mouseY, partialTicks);
		}
	}
}
