package info.u_team.u_team_core.gui;

import static info.u_team.u_team_core.render.GLTG.*;

import info.u_team.u_team_core.container.UContainer;
import info.u_team.u_team_core.render.GLSize;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * Gui API<br>
 * -> Gui Container
 * 
 * @date 17.08.2017
 * @author MrTroble
 */

public class UGuiContainer extends GuiContainer {
	
	private ResourceLocation background;
	
	public UGuiContainer(UContainer container, ResourceLocation background) {
		super(container);
		this.background = background;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GLSize size = setTexture(background);
		GlStateManager.color(1, 1, 1, 1);
		drawManual(data(vertex((xSize / 2) - (size.x / 2), (ySize / 2) - (size.y / 2), 0), vertex((xSize / 2) + (size.x / 2), (ySize / 2) - (size.y / 2), 0), vertex((xSize / 2) + (size.x / 2), (ySize / 2) + (size.y / 2), 0), vertex((xSize / 2) - (size.x / 2), (ySize / 2) + (size.y / 2), 0)), null, data(vertex(0, 0), vertex(1, 0), vertex(1, 1), vertex(0, 1)), null);
	}
	
}
