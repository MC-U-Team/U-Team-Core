package info.u_team.u_team_test;

import info.u_team.u_team_core.util.verify.JarSignVerifier;
import net.minecraftforge.fml.common.Mod;

@Mod(TestMod.MODID)
public class TestMod {
	
	public static final String MODID = "uteamtest";
	
	public TestMod() {
		JarSignVerifier.checkSigned(MODID);
		System.out.println("--------------------------------------- LOADING TEST MOD ---------------------------------------");
	}
}
