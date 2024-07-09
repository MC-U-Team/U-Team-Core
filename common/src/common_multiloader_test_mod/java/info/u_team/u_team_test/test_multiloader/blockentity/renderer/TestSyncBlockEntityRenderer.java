package info.u_team.u_team_test.test_multiloader.blockentity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_test.test_multiloader.blockentity.TestSyncBlockEntity;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.phys.Vec3;

public class TestSyncBlockEntityRenderer implements BlockEntityRenderer<TestSyncBlockEntity> {
	
	private final EntityRenderDispatcher entityRenderer;
	private final Font font;
	
	public TestSyncBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		entityRenderer = context.getEntityRenderer();
		font = context.getFont();
	}
	
	@Override
	public void render(TestSyncBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		final String displayString = "Counter: " + blockEntity.getCounter();
		poseStack.pushPose();
		
		poseStack.setIdentity();
		final Vec3 cameraPos = entityRenderer.camera.getPosition();
		final Vec3 blockPos = blockEntity.getBlockPos().getCenter();
		poseStack.translate(blockPos.x - cameraPos.x, blockPos.y + 1 - cameraPos.y, blockPos.z - cameraPos.z);
		poseStack.mulPose(entityRenderer.camera.rotation());
		poseStack.scale(0.025F, -0.025F, 0.025F);
		
		final float x = -font.width(displayString) / 2;
		font.drawInBatch(displayString, x, 0, 0xFFFF00, true, poseStack.last().pose(), bufferSource, DisplayMode.POLYGON_OFFSET, 0, 0xF000F0);
		
		poseStack.popPose();
	}
	
}
