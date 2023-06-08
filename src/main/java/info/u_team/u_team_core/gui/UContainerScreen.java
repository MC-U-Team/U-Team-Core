package info.u_team.u_team_core.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import info.u_team.u_team_core.container.UContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class UContainerScreen<T extends Container> extends ContainerScreen<T> {
	
	protected ResourceLocation background;
	protected int backgroundWidth, backgroundHeight;
	
	public UContainerScreen(T container, PlayerInventory playerInventory, ITextComponent title, ResourceLocation background) {
		super(container, playerInventory, title);
		this.background = background;
		backgroundWidth = backgroundHeight = 256;
	}
	
	public void setBackground(ResourceLocation background) {
		this.background = background;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		minecraft.getTextureManager().bindTexture(background);
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		
		blit(xStart, yStart, 0, 0, xSize, ySize, backgroundWidth, backgroundHeight);
	}
	
	@Override
	public void tick() {
		super.tick();
		if(container instanceof UContainer) {
			((UContainer) container).updateTrackedServerToClient();
		}
	}
}