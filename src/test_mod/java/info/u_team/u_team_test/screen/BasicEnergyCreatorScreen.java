package info.u_team.u_team_test.screen;

import info.u_team.u_team_core.gui.elements.EnergyStorageWidget;
import info.u_team.u_team_core.screen.UContainerMenuScreen;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.menu.BasicEnergyCreatorMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BasicEnergyCreatorScreen extends UContainerMenuScreen<BasicEnergyCreatorMenu> {
	
	private static final ResourceLocation BACKGROUND = new ResourceLocation(TestMod.MODID, "textures/gui/energy_creator.png");
	
	public BasicEnergyCreatorScreen(BasicEnergyCreatorMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title, BACKGROUND, 176, 173);
	}
	
	@Override
	protected void init() {
		super.init();
		addRenderableWidget(new EnergyStorageWidget(leftPos + 9, topPos + 20, 54, menu.getBlockEntity()::getEnergy));
	}
}
