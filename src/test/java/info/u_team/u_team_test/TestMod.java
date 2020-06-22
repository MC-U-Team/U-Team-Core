package info.u_team.u_team_test;

import info.u_team.u_team_core.util.verify.JarSignVerifier;
import info.u_team.u_team_test.init.TestBiomes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TestMod.MODID)
public class TestMod {
	
	public static final String MODID = "uteamtest";
	
	public TestMod() {
		JarSignVerifier.checkSigned(MODID);
		System.out.println("--------------------------------------- LOADING TEST MOD ---------------------------------------");
		TestBiomes.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
