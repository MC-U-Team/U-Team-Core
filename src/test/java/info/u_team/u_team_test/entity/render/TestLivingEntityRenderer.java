package info.u_team.u_team_test.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_test.TestMod;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class TestLivingEntityRenderer extends ZombieRenderer {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(TestMod.MODID, "textures/entity/test_living/test_living.png");
	
	public TestLivingEntityRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager);
	}
	
	@Override
	protected void scale(Zombie zombie, PoseStack matrixStack, float partialTickTime) {
		matrixStack.scale(1.8F, 1.8F, 1.8F);
		super.scale(zombie, matrixStack, partialTickTime);
	}
	
	@Override
	public ResourceLocation getTextureLocation(Zombie entity) {
		return TEXTURE;
	}
	
}
