package info.u_team.u_team_test.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.ZombieEntity;

public class TestLivingEntityRenderer extends ZombieRenderer {
	
	public TestLivingEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}
	
	@Override
	protected void preRenderCallback(ZombieEntity zombie, MatrixStack matrixStack, float partialTickTime) {
		matrixStack.scale(1.0625F, 1.0625F, 1.0625F);
		super.preRenderCallback(zombie, matrixStack, partialTickTime);
	}
	
}
