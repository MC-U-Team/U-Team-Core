package info.u_team.u_team_core.gui.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import info.u_team.u_team_core.util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

@Deprecated
public class FluidInventoryRender extends AbstractGui {
	
	private static final ResourceLocation ATLAS = PlayerContainer.LOCATION_BLOCKS_TEXTURE;
	
	public void drawFluid(MatrixStack matrixStack, int x, int y, FluidStack stack) {
		if (stack == null || stack.isEmpty()) {
			return;
		}
		
		final TextureAtlasSprite sprite = getFluidSprite(stack);
		Minecraft.getInstance().getTextureManager().bindTexture(ATLAS);
		
		RenderSystem.enableBlend();
		RenderSystem.enableAlphaTest();
		
		final RGBA rgba = RGBA.fromARGB(stack.getFluid().getAttributes().getColor(stack));
		RenderSystem.color4f(rgba.getRedComponent(), rgba.getGreenComponent(), rgba.getBlueComponent(), rgba.getAlphaComponent());
		
		blit(matrixStack, x, y, 100, 16, 16, sprite);
		
		RenderSystem.color4f(1, 1, 1, 1);
		
		RenderSystem.disableAlphaTest();
		RenderSystem.disableBlend();
	}
	
	private TextureAtlasSprite getFluidSprite(FluidStack stack) {
		return Minecraft.getInstance().getAtlasSpriteGetter(ATLAS).apply(stack.getFluid().getAttributes().getStillTexture(stack));
	}
}
