package info.u_team.u_team_test.init;

import info.u_team.u_team_core.registry.GuiRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.gui.GuiTileEntity;
import info.u_team.u_team_test.tileentity.TileEntityTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class TestGuis {
	
	public static void contruct() {
		
		GuiRegistry.registerTileEntity(new ResourceLocation(TestMod.modid, "tileentity"), data -> {
			TileEntity tileentity = data.getTileentity();
			if (tileentity instanceof TileEntityTileEntity) {
				TileEntityTileEntity tileentitytilentity = (TileEntityTileEntity) tileentity;
				return new GuiTileEntity(data.getPlayer().inventory, tileentitytilentity, data.getExtraNBT());
			}
			return null;
		});
	}
}
