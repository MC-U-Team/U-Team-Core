package info.u_team.u_team_test.init;

import info.u_team.u_team_test.entity.render.TestLivingEntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.IEventBus;

public class TestRenderers {

	private static void registerRenderers(RegisterRenderers event) {
		event.registerEntityRenderer(TestEntityTypes.BETTER_ENDERPEARL.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(TestEntityTypes.TEST_LIVING.get(), TestLivingEntityRenderer::new);
	}

	public static void registerMod(IEventBus bus) {
		bus.addListener(TestRenderers::registerRenderers);
	}
}
