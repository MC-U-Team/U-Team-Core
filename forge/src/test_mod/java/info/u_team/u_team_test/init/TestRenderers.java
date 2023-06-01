package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.client.EntityRendererRegister;
import info.u_team.u_team_test.entity.render.TestLivingEntityRenderer;
import net.minecraft.Util;

public class TestRenderers {
	
	public static final EntityRendererRegister ENTITY_RENDERERS = Util.make(EntityRendererRegister.create(), entityRenderers -> {
		entityRenderers.register(TestEntityTypes.TEST_LIVING, TestLivingEntityRenderer::new);
	});
	
	public static void register() {
		ENTITY_RENDERERS.register();
	}
}
