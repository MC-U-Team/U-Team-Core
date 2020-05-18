package info.u_team.u_team_core.gui;

import info.u_team.u_team_core.container.*;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public abstract class FluidContainerScreen<T extends Container> extends ContainerScreen<T> {
	
	public FluidContainerScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		if (container instanceof FluidContainer) {
			final FluidContainer fluidContainer = (FluidContainer) container;
			for (int index = 0; index < fluidContainer.fluidSlots.size(); index++) {
				drawFluidSlot(fluidContainer.fluidSlots.get(index));
			}
		}
	}
	
	protected void drawFluidSlot(FluidSlot slot) {
		// final int x = slot.getX();
		// final int y = slot.getY();
		// final FluidStack stack = slot.getStack();
		// TODO
	}
	
}
