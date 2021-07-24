package info.u_team.u_team_core.gui.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.util.GuiUtil;
import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class FluidInventoryRenderer extends AbstractGui {
	
	private static final ResourceLocation ATLAS = PlayerContainer.BLOCK_ATLAS;
	
	public void drawFluid(MatrixStack matrixStack, int x, int y, FluidStack stack) {
		if (stack == null || stack.isEmpty()) {
			return;
		}
		
		final TextureAtlasSprite sprite = getFluidSprite(stack);
		Minecraft.getInstance().getTextureManager().bind(ATLAS);
		
		RenderUtil.enableBlend();
		RenderUtil.enableAlphaTest();
		
		final RGBA rgba = RGBA.fromARGB(stack.getFluid().getAttributes().getColor(stack));
		
		GuiUtil.drawTexturedColoredQuad(matrixStack, x, y, 16, 16, sprite, 100, rgba);
		
		RenderUtil.disableAlphaTest();
		RenderUtil.disableBlend();
	}
	
	protected TextureAtlasSprite getFluidSprite(FluidStack stack) {
		return Minecraft.getInstance().getTextureAtlas(ATLAS).apply(stack.getFluid().getAttributes().getStillTexture(stack));
	}
	
}
