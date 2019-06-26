package info.u_team.u_team_test.gui;

import info.u_team.u_team_core.gui.UContainerScreen;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.BasicEnergyCreatorContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BasicEnergyCreatorScreen extends UContainerScreen<BasicEnergyCreatorContainer>{

	public BasicEnergyCreatorScreen(BasicEnergyCreatorContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title, new ResourceLocation(TestMod.MODID, "textures/gui/energy_creator.png"));
	}

}
