package info.u_team.u_team_test.init;

import info.u_team.u_team_core.registry.VillagerProfessionRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.villagerprofession.VillagerProfessionRadiation;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public class TestVillagerProfessions {
	
	public static final VillagerProfession radiation = new VillagerProfessionRadiation("radiation");
	
	public static void construct() {
		VillagerProfessionRegistry.register(TestMod.modid, TestVillagerProfessions.class);
	}
}
