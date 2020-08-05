package info.u_team.u_team_test;

import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.u_team_core.util.verify.JarSignVerifier;
import info.u_team.u_team_test.init.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(TestMod.MODID)
public class TestMod {
	
	public static final String MODID = "uteamtest";
	
	public TestMod() {
		JarSignVerifier.checkSigned(MODID);
		
		System.out.println("--------------------------------------- LOADING TEST MOD ---------------------------------------");
		
		TestCommonBusRegister.register();
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> TestClientBusRegister::register);
	}
	
	private void register() {
		BusRegister.registerMod(TestBiomes::register);
		BusRegister.registerMod(TestBlocks::register);
		BusRegister.registerMod(TestContainers::register);
		BusRegister.registerMod(TestEffects::register);
		BusRegister.registerMod(TestEnchantments::register);
		BusRegister.registerMod(TestEntityTypes::register);
		BusRegister.registerMod(TestGlobalLootModifiers::register);
		BusRegister.registerMod(TestItems::register);
		BusRegister.registerMod(TestModDimensions::register);
		BusRegister.registerMod(TestPotions::register);
		BusRegister.registerMod(TestSounds::register);
		BusRegister.registerMod(TestTileEntityTypes::register);
	}
}
