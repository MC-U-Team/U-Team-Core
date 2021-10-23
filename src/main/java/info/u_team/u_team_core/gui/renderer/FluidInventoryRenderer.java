package info.u_team.u_team_core.gui.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.fluids.FluidStack;

public class FluidInventoryRenderer extends GuiComponent {
	
	private static final ResourceLocation ATLAS = InventoryMenu.BLOCK_ATLAS;
	
	public void drawFluid(PoseStack poseStack, int x, int y, FluidStack stack) {
		if (stack == null || stack.isEmpty()) {
			return;
		}
		
		final var sprite = getFluidSprite(stack);
		final var rgba = RGBA.fromARGB(stack.getFluid().getAttributes().getColor(stack));
		
		// RenderUtil.enableAlphaTest(); // TODO Set shader?
		
		RenderUtil.drawTexturedQuad(poseStack, x, y, 16, 16, 100, sprite, rgba);
		
		// RenderUtil.disableAlphaTest();
	}
	
	protected TextureAtlasSprite getFluidSprite(FluidStack stack) {
		return Minecraft.getInstance().getTextureAtlas(ATLAS).apply(stack.getFluid().getAttributes().getStillTexture(stack));
	}
	
}
