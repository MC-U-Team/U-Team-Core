package info.u_team.u_team_test.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

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
		setTextLocation();
	}
	
	@Override
	protected void func_231160_c_() {
		super.func_231160_c_();
		func_230480_a_(new EnergyStorageWidget(guiLeft + 9, guiTop + 20, 54, container.getTileEntity().getCapability(CapabilityEnergy.ENERGY)));
	}
	
	@Override
	public void func_230430_a_(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		func_230446_a_(matrixStack);
		super.func_230430_a_(matrixStack, mouseX, mouseY, partialTicks);
		field_230710_m_.forEach(widget -> widget.func_230443_a_(matrixStack, mouseX, mouseY));
		func_230459_a_(matrixStack, mouseX, mouseY);
	}
	
	@Override
	protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {
		super.func_230451_b_(matrixStack, mouseX, mouseY);
		// font.drawString(title.getFormattedText(), 8, 6, 4210752);
		// font.drawString(playerInventory.getDisplayName().getFormattedText(), 8.0F, ySize - 94, 4210752);
	}
	
}
