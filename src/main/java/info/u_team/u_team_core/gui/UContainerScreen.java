package info.u_team.u_team_core.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class UContainerScreen<T extends Container> extends ContainerScreen<T> {
	
	protected ResourceLocation background;
	
	public UContainerScreen(T container, PlayerInventory playerInventory, ITextComponent title, ResourceLocation background) {
		super(container, playerInventory, title);
		this.background = background;
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
		
		blit(xStart, yStart, 0, 0, xSize, ySize);
	}
}
