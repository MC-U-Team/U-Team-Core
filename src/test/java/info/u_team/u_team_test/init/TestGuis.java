package info.u_team.u_team_test.init;

import info.u_team.u_team_test.gui.BasicEnergyCreatorScreen;
import info.u_team.u_team_test.gui.BasicTileEntityScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class TestGuis {

	public static void construct() {
		ScreenManager.registerFactory(TestContainers.BASIC, BasicTileEntityScreen::new);
		ScreenManager.registerFactory(TestContainers.BASIC_ENERGY_CREATOR, BasicEnergyCreatorScreen::new);
	}

}
