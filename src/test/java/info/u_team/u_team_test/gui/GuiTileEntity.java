package info.u_team.u_team_test.gui;

import info.u_team.u_team_core.gui.UGuiContainer;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.ContainerTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class GuiTileEntity extends UGuiContainer {
	
	public GuiTileEntity(InventoryPlayer inventoryPlayer) {
		super(new ContainerTileEntity(inventoryPlayer), new ResourceLocation(TestMod.modid, "textures/gui/tileentity.png"));
	}
	
}
