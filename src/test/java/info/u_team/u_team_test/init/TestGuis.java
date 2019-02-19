package info.u_team.u_team_test.init;

import info.u_team.u_team_test.gui.GuiTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.*;

public class TestGuis {
	
	public static void contruct() {
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> {
			return (openContainer) -> {
				ResourceLocation location = openContainer.getId();
				
				if (location.toString().equals("uteamtest:test")) {
					EntityPlayerSP player = Minecraft.getInstance().player;
					return new GuiTileEntity(player.inventory);
				}
				return null;
			};
		});
	}
	
}
