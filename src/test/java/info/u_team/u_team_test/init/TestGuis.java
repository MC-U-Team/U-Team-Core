package info.u_team.u_team_test.init;

import info.u_team.u_team_test.gui.GuiTileEntity;
import net.minecraft.client.gui.ScreenManager;

public class TestGuis {
	
	public static void construct() {
		ScreenManager.registerFactory(TestContainers.type, GuiTileEntity::new);
	}
	
}
