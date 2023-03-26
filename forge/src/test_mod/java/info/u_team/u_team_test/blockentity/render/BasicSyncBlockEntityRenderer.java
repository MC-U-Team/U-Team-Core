package info.u_team.u_team_test.blockentity.render;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_test.blockentity.BasicSyncBlockEntity;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;

public class BasicSyncBlockEntityRenderer implements BlockEntityRenderer<BasicSyncBlockEntity> {
	
	private final EntityRenderDispatcher entityRenderer;
	private final Font font;
	
	public BasicSyncBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		entityRenderer = context.getEntityRenderer();
		font = context.getFont();
	}
	
	@Override
	public void render(BasicSyncBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		final String displayString = "Counter: " + blockEntity.getCounter();
		poseStack.pushPose();
		
		poseStack.translate(0.5, 1.5, 0.5);
		poseStack.scale(-0.025F, -0.025F, -0.025F);
		poseStack.mulPose(entityRenderer.cameraOrientation());
		
		final float x = -font.width(displayString) / 2;
		font.draw(poseStack, displayString, x, 0, 0xFFFF00);
		
		poseStack.popPose();
	}
	
}
