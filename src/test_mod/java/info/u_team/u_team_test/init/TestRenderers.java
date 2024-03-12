package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.ClientRegistry;
import info.u_team.u_team_test.entity.render.TestLivingEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class TestRenderers {
	
	private static void setup(FMLClientSetupEvent event) {
		ClientRegistry.registerEntityRenderer(TestEntityTypes.BETTER_ENDERPEARL, manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
		ClientRegistry.registerEntityRenderer(TestEntityTypes.TEST_LIVING, TestLivingEntityRenderer::new);
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(TestRenderers::setup);
	}
}
