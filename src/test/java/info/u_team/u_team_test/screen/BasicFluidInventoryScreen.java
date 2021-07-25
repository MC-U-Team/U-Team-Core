package info.u_team.u_team_test.screen;

import info.u_team.u_team_core.screen.UBasicContainerScreen;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.BasicFluidInventoryContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BasicFluidInventoryScreen extends UBasicContainerScreen<BasicFluidInventoryContainer> {
	
	private static final ResourceLocation BACKGROUND = new ResourceLocation(TestMod.MODID, "textures/gui/fluid_inventory.png");
	
	public BasicFluidInventoryScreen(BasicFluidInventoryContainer container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title, BACKGROUND);
	}
}
