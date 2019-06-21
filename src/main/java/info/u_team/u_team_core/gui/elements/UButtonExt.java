package info.u_team.u_team_core.gui.elements;

public class UButtonExt extends UButton {
	
	public UButtonExt(int xPos, int yPos, int width, int height, String displayString) {
		super(xPos, yPos, width, height, displayString, button -> {});
	}
	
	public UButtonExt(int xPos, int yPos, int width, int height, String displayString, IPressable pressable) {
		super(xPos, yPos, width, height, displayString, pressable);
	}
	
	public void setPressable(IPressable pressable) {
		onPress = pressable;
	}
	
	public void setPressable(Runnable runnable) {
		onPress = button -> runnable.run();
	}
	
	@Override
	public void onPress() {
		if (onPress != null) {
			onPress.onPress(this);
		}
	}
}
