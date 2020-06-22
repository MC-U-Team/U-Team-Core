package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.biome.BasicBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.*;

//@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestBiomes {
	
	//public static final Biome BASIC = new BasicBiome("basic");

	public static final DeferredRegister<Biome> BLOCKS = DeferredRegister.create(ForgeRegistries.BIOMES, TestMod.MODID);

	public static final RegistryObject<BasicBiome> BASIC = BLOCKS.register("basic", () -> new BasicBiome("basic"));
	
//	@SubscribeEvent
//	public static void register(Register<Biome> event) {
//		BaseRegistryUtil.getAllRegistryEntriesAndApplyNames(TestMod.MODID, Biome.class).forEach(event.getRegistry()::register);
//	}
}
