package info.u_team.u_team_test.screen;

import info.u_team.u_team_core.screen.UBasicContainerScreen;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.BasicFluidInventoryContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class BasicFluidInventoryScreen extends UBasicContainerScreen<BasicFluidInventoryContainer> {
	
	public BasicFluidInventoryScreen(BasicFluidInventoryContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title, new ResourceLocation(TestMod.MODID, "textures/gui/fluid_inventory.png"));
	}
}
