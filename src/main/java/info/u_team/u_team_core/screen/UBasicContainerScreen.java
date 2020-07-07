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
	
	@Override
	public void func_230430_a_(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		func_230446_a_(matrixStack);
		super.func_230430_a_(matrixStack, mouseX, mouseY, partialTicks);
		field_230710_m_.forEach(widget -> widget.func_230443_a_(matrixStack, mouseX, mouseY));
		func_230459_a_(matrixStack, mouseX, mouseY);
	}
	
}
