package info.u_team.u_team_core.gui.elements;

import info.u_team.u_team_core.util.RGBA;

public class UActiveButton extends UButton {
	
	protected boolean active;
	
	protected RGBA activeColor;
	
	public UActiveButton(int x, int y, int width, int height, String displayString, int activeColor) {
		this(x, y, width, height, displayString, activeColor, EMTPY_PRESSABLE);
	}
	
	public UActiveButton(int x, int y, int width, int height, String displayString, int activeColor, IPressable pressable) {
		super(x, y, width, height, displayString, pressable);
		this.activeColor = new RGBA(activeColor);
	}
	
	public RGBA getActiveColorRGBA() {
		return activeColor;
	}
	
	public void setActiveColorRGBA(RGBA activeColor) {
		this.activeColor = activeColor;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public void renderButton(int mouseX, int mouseY, float partial) {
		if (active) {
			activeColor.glColor();
		}
		super.renderButton(mouseX, mouseY, partial);
	}
}
