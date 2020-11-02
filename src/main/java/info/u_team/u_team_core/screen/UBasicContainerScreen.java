package info.u_team.u_team_core.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class UBasicContainerScreen<T extends Container> extends UContainerScreen<T> {
	
	public UBasicContainerScreen(T container, PlayerInventory playerInventory, ITextComponent title, ResourceLocation background) {
		super(container, playerInventory, title, background);
	}
	
	public UBasicContainerScreen(T container, PlayerInventory playerInventory, ITextComponent title, ResourceLocation background, int xSize, int ySize) {
		super(container, playerInventory, title, background);
		setSize(xSize, ySize);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		buttons.forEach(widget -> widget.renderToolTip(matrixStack, mouseX, mouseY));
		renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}
	
}
