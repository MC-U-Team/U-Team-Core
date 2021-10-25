package info.u_team.u_team_core.gui.elements;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;

public abstract class ScrollableListEntry<T extends ScrollableListEntry<T>> extends ObjectSelectionList.Entry<T> {
	
	protected final Minecraft minecraft;
	
	private final List<GuiEventListener> children;
	
	public ScrollableListEntry() {
		minecraft = Minecraft.getInstance();
		children = new ArrayList<>();
	}
	
	protected <B extends GuiEventListener> B addChildren(B listener) {
		children.add(listener);
		return listener;
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		children.forEach(listener -> listener.mouseClicked(mouseX, mouseY, button));
		return true;
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		for (final var listener : children) {
			if (listener.mouseReleased(mouseX, mouseY, button)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		for (final var listener : children) {
			if (listener.mouseDragged(mouseX, mouseY, button, dragX, dragY)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public abstract void render(PoseStack poseStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks);
	
	@SuppressWarnings("deprecation")
	protected AbstractSelectionList<T> getList() {
		return list;
	}
}
