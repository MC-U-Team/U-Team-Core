package info.u_team.u_team_test.gui;

import info.u_team.u_team_core.gui.UContainerScreen;
import info.u_team.u_team_core.gui.elements.EnergyStorageWidget;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.BasicEnergyCreatorContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.energy.CapabilityEnergy;

@OnlyIn(Dist.CLIENT)
public class BasicEnergyCreatorScreen extends UContainerScreen<BasicEnergyCreatorContainer> {
	
	public BasicEnergyCreatorScreen(BasicEnergyCreatorContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title, new ResourceLocation(TestMod.MODID, "textures/gui/energy_creator.png"));
		xSize = 176;
		ySize = 173;
	}
	
	@Override
	protected void init() {
		super.init();
		addButton(new EnergyStorageWidget(guiLeft + 9, guiTop + 20, 54, container.getTileEntity().getCapability(CapabilityEnergy.ENERGY)));
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		buttons.forEach(button -> button.renderToolTip(mouseX, mouseY));
		renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		font.drawString(title.getFormattedText(), 8, 6, 4210752);
		font.drawString(playerInventory.getDisplayName().getFormattedText(), 8.0F, ySize - 94, 4210752);
	}
	
}
