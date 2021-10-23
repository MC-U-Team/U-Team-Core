package info.u_team.u_team_core.gui.elements;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ObjectSelectionList;

public abstract class ScrollableListEntry<T extends ScrollableListEntry<T>> extends ObjectSelectionList.Entry<T> {
	
	protected final Minecraft minecraft;
	
	private final List<AbstractWidget> widgets;
	
	public ScrollableListEntry() {
		minecraft = Minecraft.getInstance();
		widgets = new ArrayList<>();
	}
	
	// TODO rename to match screen
	protected <B extends AbstractWidget> B addButton(B button) {
		widgets.add(button);
		return button;
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		widgets.forEach(widget -> widget.mouseClicked(mouseX, mouseY, button));
		return true;
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		for (final var widget : widgets) {
			if (widget.mouseReleased(mouseX, mouseY, button)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		for (final var widget : widgets) {
			if (widget.mouseDragged(mouseX, mouseY, button, dragX, dragY)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public abstract void render(PoseStack poseStack, int slotIndex, int entryY, int entryX, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float partialTicks);
	
	@SuppressWarnings("deprecation")
	protected AbstractSelectionList<T> getList() {
		return list;
	}
}
