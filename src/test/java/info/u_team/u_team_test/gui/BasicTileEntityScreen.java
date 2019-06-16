package info.u_team.u_team_test.gui;

import info.u_team.u_team_core.gui.UContainerScreen;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.BasicTileEntityContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class BasicTileEntityScreen extends UContainerScreen<BasicTileEntityContainer> {
	
	public BasicTileEntityScreen(BasicTileEntityContainer container, PlayerInventory playerInventory, ITextComponent text) {
		super(container, playerInventory, text, new ResourceLocation(TestMod.modid, "textures/gui/tileentity.png"));
		xSize = 176;
		ySize = 173;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		font.drawString(title.getFormattedText(), 8, 6, 4210752);
		font.drawString(playerInventory.getDisplayName().getFormattedText(), 8.0F, ySize - 94, 4210752);
	}
}
