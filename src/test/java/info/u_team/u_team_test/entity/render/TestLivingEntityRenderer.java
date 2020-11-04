package info.u_team.u_team_test.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_test.TestMod;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.ResourceLocation;

public class TestLivingEntityRenderer extends ZombieRenderer {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(TestMod.MODID, "textures/entity/test_living/test_living.png");
	
	public TestLivingEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}
	
	@Override
	protected void preRenderCallback(ZombieEntity zombie, MatrixStack matrixStack, float partialTickTime) {
		matrixStack.scale(1.0625F, 1.0625F, 1.0625F);
		super.preRenderCallback(zombie, matrixStack, partialTickTime);
	}
	
	@Override
	public ResourceLocation getEntityTexture(ZombieEntity entity) {
		return TEXTURE;
	}
	
}
