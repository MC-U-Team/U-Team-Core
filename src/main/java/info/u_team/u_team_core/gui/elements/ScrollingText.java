package info.u_team.u_team_core.gui.elements;

import java.util.function.Supplier;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector4f;

import info.u_team.u_team_core.util.RenderUtil.Matrix4fExtended;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.util.Mth;

public class ScrollingText extends ScalableText {
	
	protected int width;
	protected float stepSize;
	protected int speedTime;
	protected int waitTime;
	
	protected float moveDifference = 0;
	protected long lastTime = 0;
	protected State state = State.WAITING;
	
	public ScrollingText(Font font, Supplier<String> textSupplier, float x, float y) {
		super(font, textSupplier, x, y);
		width = 100;
		stepSize = 1;
		speedTime = 20;
		waitTime = 4000;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public float getStepSize() {
		return stepSize;
	}
	
	public void setStepSize(float stepSize) {
		this.stepSize = stepSize;
	}
	
	public int getSpeedTime() {
		return speedTime;
	}
	
	public void setSpeedTime(int speedtime) {
		speedTime = speedtime;
	}
	
	public int getWaitTime() {
		return waitTime;
	}
	
	public void setWaitTime(int waittime) {
		waitTime = waittime;
	}
	
	public void copyState(ScrollingText renderer) {
		setText(textSupplier.get());
		state = renderer.state;
		moveDifference = renderer.moveDifference;
		lastTime = renderer.lastTime;
	}
	
	@Override
	protected void updatedText() {
		state = State.WAITING;
		moveDifference = 0;
		lastTime = 0;
		super.updatedText();
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		final Minecraft minecraft = Minecraft.getInstance();
		final Window window = minecraft.getWindow();
		
		final Matrix4fExtended matrix = new Matrix4fExtended(poseStack.last().pose());
		final double scaleFactor = window.getGuiScale();
		
		final Vector4f vectorXY = new Vector4f(x, y, 0, 1);
		vectorXY.transform(matrix);
		
		// Cannot use transform here, because we only care about the scaling. M00 and M11 should have the right scaling
		final Vector4f vectorWH = new Vector4f(width * matrix.getM00(), (font.lineHeight + 1) * scale * matrix.getM11(), 0, 1);
		
		final int nativeX = Mth.ceil(vectorXY.x() * scaleFactor);
		final int nativeY = Mth.ceil(vectorXY.y() * scaleFactor);
		
		final int nativeWidth = Mth.ceil(vectorWH.x() * scaleFactor);
		final int nativeHeight = Mth.ceil(vectorWH.y() * scaleFactor);
		
		RenderSystem.enableScissor(nativeX, window.getScreenHeight() - (nativeY + nativeHeight), nativeWidth, nativeHeight);
		
		// Uncomment to test scissor
		// poseStack.pushPose();
		// poseStack.last().pose().setIdentity();
		// GuiComponent.fill(poseStack, 0, 0, window.getGuiScaledWidth(), window.getGuiScaledHeight(), 0x8F00FF00);
		// poseStack.popPose();
		
		setText(textSupplier.get());
		renderFont(poseStack, font, getMovingX(x), y + 2 * scale);
		
		RenderSystem.disableScissor();
	}
	
	protected float getMovingX(float x) {
		final float textWidth = getTextWidth();
		if (width < textWidth) {
			final float maxMove = width - textWidth;
			
			if (lastTime == 0) {
				lastTime = System.currentTimeMillis();
			}
			
			if (state == State.WAITING) {
				if (hasWaitTimePassed()) {
					state = moveDifference >= 0 ? State.LEFT : State.RIGHT;
					lastTime = 0;
				}
			} else {
				if (hasSpeedTimePassed()) {
					if (state == State.LEFT ? moveDifference >= maxMove : moveDifference <= 0) {
						moveDifference += state == State.LEFT ? -stepSize : +stepSize;
					} else {
						state = State.WAITING;
					}
					lastTime = 0;
				}
			}
			return x + moveDifference;
		}
		return x;
	}
	
	protected boolean hasWaitTimePassed() {
		return System.currentTimeMillis() - waitTime >= lastTime;
	}
	
	protected boolean hasSpeedTimePassed() {
		return System.currentTimeMillis() - speedTime >= lastTime;
	}
	
	private enum State {
		WAITING,
		LEFT,
		RIGHT;
	}
	
}
