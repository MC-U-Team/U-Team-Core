package info.u_team.u_team_core.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import info.u_team.u_team_core.container.*;
import info.u_team.u_team_core.gui.render.FluidInventoryRender;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public abstract class FluidContainerScreen<T extends Container> extends ContainerScreen<T> {
	
	private static final FluidInventoryRender FLUID_RENDERER = new FluidInventoryRender();
	
	protected FluidInventoryRender fluidRenderer;
	
	protected FluidSlot hoveredFluidSlot;
	
	public FluidContainerScreen(T container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
		fluidRenderer = FLUID_RENDERER;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		if (container instanceof FluidContainer) {
			hoveredFluidSlot = null;
			
			final FluidContainer fluidContainer = (FluidContainer) container;
			for (int index = 0; index < fluidContainer.fluidSlots.size(); index++) {
				
				final FluidSlot fluidSlot = fluidContainer.fluidSlots.get(index);
				
				if (fluidSlot.isEnabled()) {
					drawFluidSlot(fluidSlot);
				}
				
				if (isFluidSlotSelected(fluidSlot, mouseX, mouseY) && fluidSlot.isEnabled()) {
					hoveredFluidSlot = fluidSlot;
					final int x = fluidSlot.getX();
					final int y = fluidSlot.getY();
					RenderSystem.disableDepthTest();
					RenderSystem.colorMask(true, true, true, false);
					final int slotColor = getFluidSlotColor(index);
					fillGradient(x, y, x + 16, y + 16, slotColor, slotColor);
					RenderSystem.colorMask(true, true, true, true);
					RenderSystem.enableDepthTest();
				}
			}
		}
	}
	
	protected void drawFluidSlot(FluidSlot fluidSlot) {
		fluidRenderer.drawFluid(fluidSlot.getX(), fluidSlot.getY(), fluidSlot.getStack());
	}
	
	protected boolean isFluidSlotSelected(FluidSlot fluidSlot, double mouseX, double mouseY) {
		return isPointInRegion(fluidSlot.getX(), fluidSlot.getY(), 16, 16, mouseX, mouseY);
	}
	
	public int getFluidSlotColor(int index) {
		return super.getSlotColor(index);
	}
	
}
