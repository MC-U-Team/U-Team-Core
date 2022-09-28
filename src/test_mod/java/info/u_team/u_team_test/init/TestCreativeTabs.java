package info.u_team.u_team_test.init;

import info.u_team.u_team_core.creativetab.UCreativeModeTab;
import info.u_team.u_team_test.TestMod;
import net.minecraft.world.item.CreativeModeTab;

public class TestCreativeTabs {
	
	public static final CreativeModeTab TAB = new UCreativeModeTab(TestMod.MODID, "tab", TestBlocks.BASIC);
	
}
