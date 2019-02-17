package info.u_team.u_team_test.init;

import info.u_team.u_team_core.itemgroup.UItemGroup;
import info.u_team.u_team_test.TestMod;
import net.minecraft.init.Blocks;

public class TestItemGroups {
	
	public static final UItemGroup group = new UItemGroup(TestMod.modid, "group");
	
	public static void setup() {
		System.out.println("HAHAHAHHAHAHHAHAHSDHIJSKDFHSDFHBSMDGFHKLSDJ,Fbjkh<es,mfdjgsdfgbhsdm,hjgfsdk.hjfsdjk");
		group.setIcon(Blocks.STONE);
	}
	
}
