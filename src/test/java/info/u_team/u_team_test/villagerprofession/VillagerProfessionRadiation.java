package info.u_team.u_team_test.villagerprofession;

import info.u_team.u_team_core.villagerprofession.UVillagerProfession;
import info.u_team.u_team_test.TestMod;

public class VillagerProfessionRadiation extends UVillagerProfession {
	
	public VillagerProfessionRadiation(String name) {
		super(TestMod.modid, name);
		new VillagerCareerRadiation(this);
	}
	
}
