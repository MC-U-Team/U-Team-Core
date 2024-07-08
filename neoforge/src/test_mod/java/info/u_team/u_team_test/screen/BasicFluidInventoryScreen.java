package info.u_team.u_team_test.screen;

import info.u_team.u_team_core.screen.UContainerMenuScreen;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.menu.BasicFluidInventoryMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BasicFluidInventoryScreen extends UContainerMenuScreen<BasicFluidInventoryMenu> {
	
	private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(TestMod.MODID, "textures/gui/fluid_inventory.png");
	
	public BasicFluidInventoryScreen(BasicFluidInventoryMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title, BACKGROUND);
	}
}
