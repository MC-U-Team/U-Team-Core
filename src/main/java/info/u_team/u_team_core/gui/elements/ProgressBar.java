package info.u_team.u_team_core.gui.elements;

import java.util.function.*;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.SoundEvents;

public class ProgressBar extends AbstractGui implements IGuiEventListener, IRenderable {
	
	private Supplier<Double> progress;
	private Consumer<Double> click;
	
	private int width = 200;
	private int height = 5;
	private int x;
	private int y;
	
	private int backgroundColor;
	private int progressColor;
	
	private boolean enabled = true;
	private boolean visible = true;
	
	private boolean hovered;
	
	public ProgressBar(int x, int y, int width, int height, int backgroundColor, int progressColor, Supplier<Double> progress, Consumer<Double> click) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.backgroundColor = backgroundColor;
		this.progressColor = progressColor;
		this.progress = progress;
		this.click = click;
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		if (visible) {
			hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			
			fill(matrixStack, x, y, x + width, y + height, backgroundColor);
			fill(matrixStack, x, y, (int) (x + (progress.get() * width)), y + height, progressColor);
		}
	}
	
	public void onClick(double mouseX, double mouseY) {
		if (click != null) {
			click.accept((mouseX - x) / width);
		}
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == 0) {
			if (isPressable(mouseX, mouseY)) {
				playPressSound(Minecraft.getInstance().getSoundHandler());
				onClick(mouseX, mouseY);
				return true;
			}
		}
		return false;
	}
	
	protected boolean isPressable(double mouseX, double mouseY) {
		return enabled && visible && mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
	}
	
	public void playPressSound(SoundHandler soundHandler) {
		soundHandler.play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
	}
	
	public void setProgressSupplier(Supplier<Double> progress) {
		this.progress = progress;
	}
	
	public double getProgress() {
		return progress.get();
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isHovered() {
		return hovered;
	}
	
	public int getBackgroundColor() {
		return backgroundColor;
	}
	
	public void setBackgroundColor(int backGroundColor) {
		this.backgroundColor = backGroundColor;
	}
	
	public int getProgressColor() {
		return progressColor;
	}
	
	public void setProgressColor(int progressColor) {
		this.progressColor = progressColor;
	}
	
	public void setClick(Consumer<Double> click) {
		this.click = click;
	}
	
}
