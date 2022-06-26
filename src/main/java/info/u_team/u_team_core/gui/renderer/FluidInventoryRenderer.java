package info.u_team.u_team_core.gui.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.IFluidTypeRenderProperties;
import net.minecraftforge.client.RenderProperties;
import net.minecraftforge.fluids.FluidStack;

public class FluidInventoryRenderer {
	
	public static final FluidInventoryRenderer DEFAULT_INSTANCE = new FluidInventoryRenderer();
	
	private static final ResourceLocation ATLAS = InventoryMenu.BLOCK_ATLAS;
	
	public void drawFluidInSlot(PoseStack poseStack, int x, int y, float blitOffset, FluidStack stack) {
		if (stack == null || stack.isEmpty()) {
			return;
		}
		
		final IFluidTypeRenderProperties renderProperties = RenderProperties.get(stack.getFluid());
		
		final TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(ATLAS).apply(renderProperties.getStillTexture(stack));
		final RGBA rgba = RGBA.fromARGB(renderProperties.getColorTint(stack));
		
		RenderUtil.drawTexturedQuad(poseStack, x, y, 16, 16, blitOffset, sprite, rgba);
		
		RenderUtil.setShaderColor(RGBA.WHITE);
	}
	
}
