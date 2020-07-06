package info.u_team.u_team_test.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.gui.UContainerScreen;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.BasicFluidInventoryContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BasicFluidInventoryScreen extends UContainerScreen<BasicFluidInventoryContainer> {
	
	public BasicFluidInventoryScreen(BasicFluidInventoryContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title, new ResourceLocation(TestMod.MODID, "textures/gui/fluid_inventory.png"));
	}
	
	@Override
	public void func_230430_a_(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		func_230446_a_(matrixStack);
		super.func_230430_a_(matrixStack, mouseX, mouseY, partialTicks);
		//buttons.forEach(button -> button.renderToolTip(mouseX, mouseY)); TODO nessessary??
		func_230459_a_(matrixStack, mouseX, mouseY);
	}
	
//	@Override
//	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
//		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
//		font.drawString(title.getFormattedText(), 8, 6, 4210752);
//		font.drawString(playerInventory.getDisplayName().getFormattedText(), 8, ySize - 94, 4210752);
//	}
	
}
