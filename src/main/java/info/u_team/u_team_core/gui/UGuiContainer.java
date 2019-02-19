package info.u_team.u_team_core.gui;

import info.u_team.u_team_core.container.UContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class UGuiContainer extends GuiContainer {
	
	protected final ResourceLocation background;
	
	public UGuiContainer(UContainer container, ResourceLocation background) {
		super(container);
		this.background = background;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(background);
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		
		drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
		
	}
	
}
