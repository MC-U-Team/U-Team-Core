package info.u_team.u_team_core.gui.elements;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvents;

public class ProgressBar implements GuiEventListener, Widget {
	
	protected Supplier<Double> progress;
	protected Consumer<Double> click;
	
	protected int width = 200;
	protected int height = 5;
	protected int x;
	protected int y;
	
	protected RGBA backgroundColor;
	protected RGBA progressColor;
	
	protected boolean enabled = true;
	protected boolean visible = true;
	
	protected boolean hovered;
	
	public ProgressBar(int x, int y, int width, int height, RGBA backgroundColor, RGBA progressColor, Supplier<Double> progress, Consumer<Double> click) {
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
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		if (visible) {
			hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
			
			GuiComponent.fill(poseStack, x, y, x + width, y + height, backgroundColor.getColorARGB());
			GuiComponent.fill(poseStack, x, y, (int) (x + (progress.get() * width)), y + height, progressColor.getColorARGB());
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
				playPressSound(Minecraft.getInstance().getSoundManager());
				onClick(mouseX, mouseY);
				return true;
			}
		}
		return false;
	}
	
	protected boolean isPressable(double mouseX, double mouseY) {
		return enabled && visible && mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
	}
	
	public void playPressSound(SoundManager soundHandler) {
		soundHandler.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
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
	
	public RGBA getBackgroundColor() {
		return backgroundColor;
	}
	
	public void setBackgroundColor(RGBA backGroundColor) {
		backgroundColor = backGroundColor;
	}
	
	public RGBA getProgressColor() {
		return progressColor;
	}
	
	public void setProgressColor(RGBA progressColor) {
		this.progressColor = progressColor;
	}
	
	public void setClick(Consumer<Double> click) {
		this.click = click;
	}
	
}
