package info.u_team.u_team_core.gui.elements;

import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiButtonClick extends GuiButtonExt {
	
	private Runnable runnable;
	
	public GuiButtonClick(int xPos, int yPos, int width, int height, String displayString) {
		super(xPos, yPos, width, height, displayString, button -> {});
	}
	
	public GuiButtonClick(int xPos, int yPos, int width, int height, String displayString, IPressable pressable) {
		super(xPos, yPos, width, height, displayString, pressable);
	}
	
	public void setClickAction(Runnable runnable) {
		this.runnable = runnable;
	}
	
	@Override
	public void onPress() {
		super.onPress();
		if (runnable != null) {
			runnable.run();
		}
	}
}
