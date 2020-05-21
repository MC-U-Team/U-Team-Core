package info.u_team.u_team_core.gui.render;

import com.mojang.blaze3d.systems.RenderSystem;

import info.u_team.u_team_core.util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class FluidInventoryRender {
	
	private static final ResourceLocation ATLAS = PlayerContainer.LOCATION_BLOCKS_TEXTURE;
	
	public void drawFluid(int x, int y, FluidStack stack) {
		if (stack == null || stack.isEmpty() || stack.getFluid() == Fluids.EMPTY) {
			return;
		}
		
		final TextureAtlasSprite sprite = getFluidSprite(stack);
		Minecraft.getInstance().getTextureManager().bindTexture(ATLAS);
		
		RenderSystem.enableBlend();
		RenderSystem.enableAlphaTest();
		
		final RGBA rgba = RGBA.fromARGB(stack.getFluid().getAttributes().getColor(stack));
		RenderSystem.color4f(rgba.getRedComponent(), rgba.getGreenComponent(), rgba.getBlueComponent(), rgba.getAlphaComponent());
		
		drawTextureWithMasking(x, y, sprite, 0, 0, 100);
		
		RenderSystem.color4f(1, 1, 1, 1);
		
		RenderSystem.disableAlphaTest();
		RenderSystem.disableBlend();
	}
	
	private TextureAtlasSprite getFluidSprite(FluidStack stack) {
		return Minecraft.getInstance().getAtlasSpriteGetter(ATLAS).apply(stack.getFluid().getAttributes().getStillTexture(stack));
	}
	
	private void drawTextureWithMasking(double xCoord, double yCoord, TextureAtlasSprite textureSprite, int maskTop, int maskRight, double zLevel) {
		double uMin = textureSprite.getMinU();
		double uMax = textureSprite.getMaxU();
		double vMin = textureSprite.getMinV();
		double vMax = textureSprite.getMaxV();
		uMax = uMax - (maskRight / 16.0 * (uMax - uMin));
		vMax = vMax - (maskTop / 16.0 * (vMax - vMin));
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferBuilder.pos(xCoord, yCoord + 16, zLevel).tex((float) uMin, (float) vMax).endVertex();
		bufferBuilder.pos(xCoord + 16 - maskRight, yCoord + 16, zLevel).tex((float) uMax, (float) vMax).endVertex();
		bufferBuilder.pos(xCoord + 16 - maskRight, yCoord + maskTop, zLevel).tex((float) uMax, (float) vMin).endVertex();
		bufferBuilder.pos(xCoord, yCoord + maskTop, zLevel).tex((float) uMin, (float) vMin).endVertex();
		tessellator.draw();
	}
	
}
