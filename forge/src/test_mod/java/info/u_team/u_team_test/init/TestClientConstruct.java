package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_test.TestMod;

@Construct(modid = TestMod.MODID, client = true)
public class TestClientConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		TestColors.register();
		TestScreens.register();
	}
	
}
