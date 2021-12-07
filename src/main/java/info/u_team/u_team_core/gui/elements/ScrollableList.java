package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;

public abstract class ScrollableList<T extends ObjectSelectionList.Entry<T>> extends ObjectSelectionList<T> {
	
	protected int sideDistance;
	
	protected boolean useScissor;
	protected boolean renderTransparentBorder;
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
		updateSettings(width, minecraft.getWindow().getGuiScaledHeight(), y, y + height, x, x + width);
	}
	
	public void updateSettings(int width, int height, int top, int bottom, int left, int right) {
		this.width = width;
		this.height = height;
		y0 = top;
		y1 = bottom;
		x0 = left;
		x1 = right;
	}
	
	public int getSideDistance() {
		return sideDistance;
	}
	
	public void setSideDistance(int sideDistance) {
		this.sideDistance = sideDistance;
	}
	
	public boolean isUseScissor() {
		return useScissor;
	}
	
	public void setUseScissor(boolean useScissor) {
		this.useScissor = useScissor;
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
		return x0 + width - 5;
	}
	
	@Override
	protected void renderList(PoseStack poseStack, int rowLeft, int scrollAmount, int mouseX, int mouseY, float partialTicks) {
		if (useScissor) {
			final var window = minecraft.getWindow();
			final var scaleFactor = window.getGuiScale();
			
			final var nativeX = Mth.ceil(x0 * scaleFactor);
			final var nativeY = Mth.ceil(y0 * scaleFactor);
			
			final var nativeWidth = Mth.ceil((x1 - x0) * scaleFactor);
			final var nativeHeight = Mth.ceil((y1 - y0) * scaleFactor);
			
			RenderSystem.enableScissor(nativeX, window.getScreenHeight() - (nativeY + nativeHeight), nativeWidth, nativeHeight);
			
			// Uncomment to test scissor
			// poseStack.pushPose();
			// poseStack.last().pose().setIdentity();
			// GuiComponent.fill(poseStack, 0, 0, window.getGuiScaledWidth(), window.getGuiScaledHeight(), 0x8F00FF00);
			// poseStack.popPose();
			
			super.renderList(poseStack, rowLeft, scrollAmount, mouseX, mouseY, partialTicks);
			
			RenderSystem.disableScissor();
		} else {
			super.renderList(poseStack, rowLeft, scrollAmount, mouseX, mouseY, partialTicks);
		}
		
		if (renderTransparentBorder) {
			final var tessellator = Tesselator.getInstance();
			final var buffer = tessellator.getBuilder();
			
			RenderSystem.setShader(GameRenderer::getPositionColorShader);
			RenderUtil.setShaderColor(RGBA.BLACK);
			
			RenderSystem.enableBlend();
			RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ZERO, DestFactor.ONE);
			RenderSystem.disableTexture();
			
			buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
			buffer.vertex(x0, y0 + transparentBorderSize, 0).color(0, 0, 0, 0).endVertex();
			buffer.vertex(x1, y0 + transparentBorderSize, 0).color(0, 0, 0, 0).endVertex();
			buffer.vertex(x1, y0, 0).color(0, 0, 0, 255).endVertex();
			buffer.vertex(x0, y0, 0).color(0, 0, 0, 255).endVertex();
			buffer.vertex(x0, y1, 0).color(0, 0, 0, 255).endVertex();
			buffer.vertex(x1, y1, 0).color(0, 0, 0, 255).endVertex();
			buffer.vertex(x1, y1 - transparentBorderSize, 0).color(0, 0, 0, 0).endVertex();
			buffer.vertex(x0, y1 - transparentBorderSize, 0).color(0, 0, 0, 0).endVertex();
			
			tessellator.end();
			
			RenderSystem.enableTexture();
			RenderSystem.disableBlend();
			
			RenderUtil.setShaderColor(RGBA.WHITE);
		}
	}
}
