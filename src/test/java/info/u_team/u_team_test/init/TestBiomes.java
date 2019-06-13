package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.BiomeRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.biome.BiomeBasic;
import net.minecraft.world.biome.Biome;

public class TestBiomes {
	
	public static final Biome basic = new BiomeBasic("basic");
	
	public static void construct() {
		BiomeRegistry.register(TestMod.modid, TestBiomes.class);
	}
}
