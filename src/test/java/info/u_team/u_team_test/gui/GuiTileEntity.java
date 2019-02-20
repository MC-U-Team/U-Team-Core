package info.u_team.u_team_test.gui;

import info.u_team.u_team_core.gui.UGuiContainer;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.ContainerTileEntity;
import info.u_team.u_team_test.tileentity.TileEntityTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class GuiTileEntity extends UGuiContainer {
	
	public GuiTileEntity(InventoryPlayer inventoryPlayer, TileEntityTileEntity tileentity) {
		super(new ContainerTileEntity(inventoryPlayer, tileentity), new ResourceLocation(TestMod.modid, "textures/gui/tileentity.png"));
	}
	
}
