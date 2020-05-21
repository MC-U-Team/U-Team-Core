package info.u_team.u_team_core.gui.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class FluidInventoryRender {
	
	private static final ResourceLocation ATLAS = PlayerContainer.LOCATION_BLOCKS_TEXTURE;
	
	private static TextureAtlasSprite getFluidSprite(FluidStack fluidStack) {
		return Minecraft.getInstance().getAtlasSpriteGetter(ATLAS).apply(fluidStack.getFluid().getAttributes().getStillTexture(fluidStack));
	}
	
}
