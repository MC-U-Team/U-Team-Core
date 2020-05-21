package info.u_team.u_team_core.gui;

import info.u_team.u_team_core.container.*;
import info.u_team.u_team_core.gui.render.FluidInventoryRender;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fluids.FluidStack;

@OnlyIn(Dist.CLIENT)
public abstract class FluidContainerScreen<T extends Container> extends ContainerScreen<T> {
	
	private static final FluidInventoryRender FLUID_RENDERER = new FluidInventoryRender();
	
	public FluidContainerScreen(T container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		if (container instanceof FluidContainer) {
			final FluidContainer fluidContainer = (FluidContainer) container;
			for (int index = 0; index < fluidContainer.fluidSlots.size(); index++) {
				// drawFluidSlot(fluidContainer.fluidSlots.get(index));
			}
		}
		
		FLUID_RENDERER.drawFluid(8, 18, new FluidStack(Fluids.WATER, 10));
	}
	
	protected void drawFluidSlot(FluidSlot slot) {
		final int x = slot.getX();
		final int y = slot.getY();
		final FluidStack stack = slot.getStack();
		
		FLUID_RENDERER.drawFluid(x, y, stack);
	}
	
}
