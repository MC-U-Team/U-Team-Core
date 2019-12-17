package info.u_team.u_team_test.init;

import info.u_team.u_team_test.TestMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class TestModels {
	
	@SubscribeEvent
	public static void register(FMLClientSetupEvent event) {
		// TODO disabled because of https://github.com/MinecraftForge/MinecraftForge/pull/6364
		// ClientRegistry.registerEntityRenderer(TestEntityTypes.BETTER_ENDERPEARL, manager -> new SpriteRenderer<>(manager,
		// Minecraft.getInstance().getItemRenderer()));
	}
}
