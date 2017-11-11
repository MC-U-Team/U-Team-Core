package info.u_team.u_team_core.gui;

import static info.u_team.u_team_core.render.GLTG.*;

import info.u_team.u_team_core.container.UContainer;
import info.u_team.u_team_core.render.*;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;

/**
 * Gui API<br>
 * -> Gui Container
 * 
 * @date 17.08.2017
 * @author MrTroble
 *
 */

public class UGuiContainer extends GuiContainer {
	
	private GLTG gltg;
	private GuiRescourceLocation background;
	
	public UGuiContainer(UContainer inventorySlotsIn, GuiRescourceLocation background) {
		super(inventorySlotsIn);
		gltg = getGLTG();
		this.background = background;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GLSize size = gltg.setTexture(background);
		GlStateManager.translate((width - size.x) / 2, (height - size.y) / 2, 0);
		gltg.drawManual(data(vertex(0, 0, 0), vertex(size.x, 0, 0), vertex(size.x, size.y, 0), vertex(0, size.y, 0)), null, data(vertex(0, 0), vertex(1, 0), vertex(1, 1), vertex(0, 1)), null);
	}
	
}
