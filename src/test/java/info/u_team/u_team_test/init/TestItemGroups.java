package info.u_team.u_team_test.init;

import info.u_team.u_team_core.itemgroup.UItemGroup;
import info.u_team.u_team_test.TestMod;
import net.minecraft.item.ItemGroup;

public class TestItemGroups {
	
	public static final ItemGroup GROUP = new UItemGroup(TestMod.MODID, "group", () -> TestBlocks.BASIC.get());
	
}
