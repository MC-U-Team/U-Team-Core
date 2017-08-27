package info.u_team.u_team_core.container;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

/**
 * Gui API<br>
 * -> Gui Container
 * 
 * @date 17.08.2017
 * @author MrTroble
 *
 */

public class UGuiContainer extends GuiContainer {
	
	public UGuiContainer(Container inventorySlotsIn) {
		super(inventorySlotsIn);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
	}
	
}
