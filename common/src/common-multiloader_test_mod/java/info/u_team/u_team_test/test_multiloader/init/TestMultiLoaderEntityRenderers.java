package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.client.EntityRendererRegister;
import info.u_team.u_team_test.test_multiloader.blockentity.renderer.TestSyncBlockEntityRenderer;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class TestMultiLoaderEntityRenderers {
	
	public static final EntityRendererRegister ENTITY_RENDERERS = Util.make(EntityRendererRegister.create(), entityRenderers -> {
		entityRenderers.register(TestMultiLoaderBlockEntityTypes.TEST_SYNC, TestSyncBlockEntityRenderer::new);
		
		entityRenderers.register(TestMultiLoaderEntityTypes.TEST_ENDERPEARL, ThrownItemRenderer::new);
	});
	
	static void register() {
		ENTITY_RENDERERS.register();
	}
	
}
