package info.u_team.u_team_core.gui.render;

import java.util.Objects;
import java.util.function.Supplier;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.FontRenderer;

public class ScalingTextRender {
	
	protected final Supplier<FontRenderer> fontRenderSupplier;
	
	protected Supplier<String> textSupplier;
	
	private String text;
	private int textWidth;
	
	protected int color;
	protected boolean shadow;
	protected float scale;
	private float positionFactor;
	
	public ScalingTextRender(Supplier<FontRenderer> fontRenderSupplier, Supplier<String> textSupplier) {
		this.fontRenderSupplier = fontRenderSupplier;
		this.textSupplier = textSupplier;
		scale = 1;
		positionFactor = 1;
	}
	
	public void setTextSupplier(Supplier<String> textSupplier) {
		Objects.requireNonNull(textSupplier);
		this.textSupplier = textSupplier;
	}
	
	public Supplier<String> getTextSupplier() {
		return textSupplier;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public int getColor() {
		return color;
	}
	
	public void setShadow(boolean shadow) {
		this.shadow = shadow;
	}
	
	public boolean isShadow() {
		return shadow;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
		positionFactor = 1 / scale;
	}
	
	public float getScale() {
		return scale;
	}
	
	public float getTextWidth() {
		if (textWidth == 0) { // If text width has never been set
			setText(textSupplier.get());
		}
		return textWidth * scale;
	}
	
	private void setText(String newText) {
		if ((newText != null && !newText.equals(text)) || newText == null) {
			this.text = newText;
			this.textWidth = fontRenderSupplier.get().getStringWidth(newText);
			updatedText();
		}
	}
	
	protected void updatedText() {
	}
	
	public void draw(MatrixStack matrixStack, float x, float y) {
		// Get new text and set if has changed
		setText(textSupplier.get());
		renderFont(matrixStack, x, y);
	}
	
	protected void renderFont(MatrixStack matrixStack, float x, float y) {
		matrixStack.push();
		matrixStack.scale(scale, scale, 0);
		fontRenderSupplier.get().renderString(text, x * positionFactor, y * positionFactor, color, matrixStack.getLast().getMatrix(), shadow, fontRenderSupplier.get().getBidiFlag());
		matrixStack.pop();
		renderFont(x, y);
	}
	
	@Deprecated
	public void draw(float x, float y) {
		draw(new MatrixStack(), x, y);
	}
	
	@Deprecated
	protected void renderFont(float x, float y) {
	}
}
