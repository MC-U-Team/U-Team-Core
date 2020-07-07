package info.u_team.u_team_test.screen;

import info.u_team.u_team_core.gui.elements.EnergyStorageWidget;
import info.u_team.u_team_core.screen.UBasicContainerScreen;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.BasicEnergyCreatorContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.energy.CapabilityEnergy;

@OnlyIn(Dist.CLIENT)
public class BasicEnergyCreatorScreen extends UBasicContainerScreen<BasicEnergyCreatorContainer> {
	
	private static final ResourceLocation BACKGROUND = new ResourceLocation(TestMod.MODID, "textures/gui/energy_creator.png");
	
	public BasicEnergyCreatorScreen(BasicEnergyCreatorContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title, BACKGROUND, 176, 173);
	}
	
	@Override
	protected void func_231160_c_() {
		super.func_231160_c_();
		func_230480_a_(new EnergyStorageWidget(guiLeft + 9, guiTop + 20, 54, container.getTileEntity().getCapability(CapabilityEnergy.ENERGY)));
	}
}
