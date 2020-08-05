package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.biome.BasicBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class TestBiomes {
	
	public static final CommonDeferredRegister<Biome> BIOMES = CommonDeferredRegister.create(ForgeRegistries.BIOMES, TestMod.MODID);
	
	public static final RegistryObject<BasicBiome> BASIC = BIOMES.register("basic", BasicBiome::new);
	
	public static void registerMod(IEventBus bus) {
		BIOMES.register(bus);
	}
}
