package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.client.EntityRendererRegister;
import info.u_team.u_team_test.entity.render.TestLivingEntityRenderer;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class TestRenderers {
	
	public static final EntityRendererRegister ENTITY_RENDERERS = Util.make(EntityRendererRegister.create(), entityRenderers -> {
		entityRenderers.register(TestEntityTypes.BETTER_ENDERPEARL, ThrownItemRenderer::new);
		entityRenderers.register(TestEntityTypes.TEST_LIVING, TestLivingEntityRenderer::new);
	});
	
	public static void register() {
		ENTITY_RENDERERS.register();
	}
}
