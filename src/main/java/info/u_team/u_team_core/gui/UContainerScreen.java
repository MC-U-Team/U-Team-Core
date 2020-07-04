package info.u_team.u_team_core.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import info.u_team.u_team_core.container.UContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class UContainerScreen<T extends Container> extends FluidContainerScreen<T> {
	
	protected ResourceLocation background;
	protected int backgroundWidth, backgroundHeight;
	
	public UContainerScreen(T container, PlayerInventory playerInventory, ITextComponent title, ResourceLocation background) {
		super(container, playerInventory, title);
		this.background = background;
		backgroundWidth = backgroundHeight = 256;
	}
	
	public void setBackground(ResourceLocation background) {
		this.background = background;
	}
	
	@Override
	protected void func_230450_a_(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1, 1, 1, 1);
		field_230706_i_.getTextureManager().bindTexture(background);
		final int xStart = (field_230708_k_ - xSize) / 2;
		final int yStart = (field_230709_l_ - ySize) / 2;
		
		func_238463_a_(matrixStack, xStart, yStart, 0, 0, xSize, ySize, backgroundWidth, backgroundHeight);
	}
	
	@Override
	public void func_231023_e_() {
		super.func_231023_e_();
		if (container instanceof UContainer) {
			((UContainer) container).updateTrackedServerToClient();
		}
	}
}
