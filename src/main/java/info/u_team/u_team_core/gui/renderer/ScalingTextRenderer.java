package info.u_team.u_team_core.gui.renderer;

import java.util.Objects;
import java.util.function.*;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.util.RGBA;
import net.minecraft.client.gui.*;

public class ScalingTextRenderer implements IRenderable {
	
	protected static final Consumer<ScalingTextRenderer> EMPTY_TEXT_CHANGED = renderer -> {
	};
	
	protected final FontRenderer fontRenderer;
	
	protected Supplier<String> textSupplier;
	
	protected float x;
	protected float y;
	
	private String text;
	private int textWidth;
	
	protected RGBA color;
	protected boolean shadow;
	protected float scale;
	private float positionFactor;
	
	protected Consumer<ScalingTextRenderer> textChanged;
	
	public ScalingTextRenderer(FontRenderer fontRenderer, Supplier<String> textSupplier, float x, float y) {
		this.fontRenderer = fontRenderer;
		this.textSupplier = textSupplier;
		this.x = x;
		this.y = y;
		color = RGBA.WHITE;
		scale = 1;
		positionFactor = 1;
		textChanged = EMPTY_TEXT_CHANGED;
	}
	
	public void setTextSupplier(Supplier<String> textSupplier) {
		Objects.requireNonNull(textSupplier);
		this.textSupplier = textSupplier;
	}
	
	public Supplier<String> getTextSupplier() {
		return textSupplier;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public RGBA getColor() {
		return color;
	}
	
	public void setColor(RGBA color) {
		this.color = color;
	}
	
	public boolean isShadow() {
		return shadow;
	}
	
	public void setShadow(boolean shadow) {
		this.shadow = shadow;
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
		positionFactor = 1 / scale;
	}
	
	public void setTextChanged(Consumer<ScalingTextRenderer> textChanged) {
		this.textChanged = textChanged;
	}
	
	public float getTextWidth() {
		if (textWidth == 0) { // If text width has never been set
			setText(textSupplier.get());
		}
		return textWidth * scale;
	}
	
	protected void setText(String newText) {
		if ((newText != null && !newText.equals(text)) || newText == null) {
			this.text = newText;
			this.textWidth = fontRenderer.getStringWidth(newText);
			updatedText();
		}
	}
	
	protected void updatedText() {
		textChanged.accept(this);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		// Get new text and set if has changed
		setText(textSupplier.get());
		renderFont(matrixStack, fontRenderer, x, y);
	}
	
	protected void renderFont(MatrixStack matrixStack, FontRenderer fontRenderer, float x, float y) {
		matrixStack.push();
		matrixStack.scale(scale, scale, 0);
		fontRenderer.renderString(text, x * positionFactor, y * positionFactor, color.getColorARGB(), matrixStack.getLast().getMatrix(), shadow, fontRenderer.getBidiFlag());
		matrixStack.pop();
	}
}
