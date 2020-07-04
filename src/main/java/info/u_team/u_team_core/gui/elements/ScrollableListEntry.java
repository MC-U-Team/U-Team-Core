package info.u_team.u_team_core.gui.elements;

import java.util.*;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.list.ExtendedList;

public abstract class ScrollableListEntry<T extends ScrollableListEntry<T>> extends ExtendedList.AbstractListEntry<T> {
	
	protected final Minecraft minecraft;
	
	private final List<Widget> widget;
	
	public ScrollableListEntry() {
		minecraft = Minecraft.getInstance();
		widget = new ArrayList<>();
	}
	
	protected <B extends Widget> B addButton(B button) {
		widget.add(button);
		return button;
	}
	
	@Override
	public boolean func_231044_a_(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		widget.forEach(button -> button.func_231044_a_(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_));
		return true;
	}
	
	@Override
	public boolean func_231048_c_(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
		for (final Widget button : widget) {
			if (button.func_231048_c_(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean func_231045_a_(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
		for (final Widget button : widget) {
			if (button.func_231045_a_(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_, p_mouseDragged_8_)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public abstract void func_230432_a_(MatrixStack matrixStack, int slotIndex, int entryY, int entryX, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float partialTicks);
}