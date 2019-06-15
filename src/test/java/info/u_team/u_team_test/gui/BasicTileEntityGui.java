package info.u_team.u_team_test.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.BasicContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class BasicTileEntityGui extends ContainerScreen<BasicContainer> {
	
	private PlayerInventory inventoryPlayer;
	
	public BasicTileEntityGui(BasicContainer container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		xSize = 176;
		ySize = 173;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		font.drawString(title.getFormattedText(), 8, 6, 4210752);
//		font.drawString(inventoryPlayer.getDisplayName().getFormattedText(), 8.0F, ySize - 94, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		minecraft.getTextureManager().bindTexture(new ResourceLocation(TestMod.modid, "textures/gui/tileentity.png"));
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		
		blit(xStart, yStart, 0, 0, xSize, ySize);
	}
	
}
