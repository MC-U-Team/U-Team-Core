package info.u_team.u_team_core.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.*;

public abstract class ScrollableList<T extends AbstractList.AbstractListEntry<T>> extends ExtendedList<T> {
	
	protected int listWidth;
	protected int scrollbarPos;
	
	public ScrollableList(int width, int height, int top, int bottom, int left, int right, int slotHeight, int listWidth, int scrollbarPos) {
		super(Minecraft.getInstance(), 0, 0, 0, 0, slotHeight);
		updateSettings(width, height, top, bottom, left, right);
		this.listWidth = listWidth;
		this.scrollbarPos = scrollbarPos;
	}
	
	public void updateSettings(int width, int height, int top, int bottom, int left, int right) {
		this.field_230670_d_ = width;
		this.field_230671_e_ = height;
		this.field_230672_i_ = top;
		this.field_230673_j_ = bottom;
		this.field_230675_l_ = left;
		this.field_230674_k_ = right;
	}
	
	@Override
	public int func_230949_c_() {
		return field_230670_d_ - listWidth;
	}
	
	@Override
	protected int func_230952_d_() {
		return field_230670_d_ + scrollbarPos;
	}
}
