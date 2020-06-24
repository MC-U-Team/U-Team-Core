package info.u_team.u_team_test.init;

import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.biome.BasicBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

public class TestBiomes {
	
	public static final DeferredRegister<Biome> BLOCKS = DeferredRegister.create(ForgeRegistries.BIOMES, TestMod.MODID);
	
	public static final RegistryObject<BasicBiome> BASIC = BLOCKS.register("basic", () -> {
		System.out.println("CREATE BIOME");
		return new BasicBiome("basic");
	});
	
	public static void register(IEventBus bus) {
		BLOCKS.register(bus);
	}
}
