package info.u_team.u_team_core.menu;

import com.mojang.datafixers.util.Pair;

import info.u_team.u_team_core.api.fluid.FluidHandlerModifiable;
import info.u_team.u_team_core.inventory.UFluidStackHandler;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;

public class FluidSlot {
	
	private final FluidHandlerModifiable fluidHandler;
	private final int slot;
	private final int x;
	private final int y;
	
	public int index;
	
	private Pair<ResourceLocation, ResourceLocation> backgroundPair;
	
	public FluidSlot(FluidHandlerModifiable fluidHandler, int slot, int x, int y) {
		this.fluidHandler = fluidHandler;
		this.slot = slot;
		this.x = x;
		this.y = y;
	}
	
	public boolean mayPlace(FluidStack stack) {
		if (stack.isEmpty()) {
			return false;
		}
		return fluidHandler.isFluidValid(slot, stack);
	}
	
	public FluidStack getFluid() {
		return fluidHandler.getFluidInTank(slot);
	}
	
	public boolean hasFluid() {
		return !getFluid().isEmpty();
	}
	
	public void set(FluidStack stack) {
		fluidHandler.setFluidInTank(slot, stack);
		setChanged();
	}
	
	public void setChanged() {
		if (fluidHandler instanceof final UFluidStackHandler uFluidHandler) {
			uFluidHandler.onContentsChanged(slot);
		}
	}
	
	public int getSlotCapacity() {
		return fluidHandler.getTankCapacity(slot);
	}
	
	public int getRemainingSlotCapacity() {
		return getSlotCapacity() - getFluid().getAmount();
	}
	
	public FluidHandlerModifiable getFluidHandler() {
		return fluidHandler;
	}
	
	public int getContainerSlot() {
		return slot;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isActive() {
		return true;
	}
	
	/**
	 * Sets the background atlas and sprite location.
	 *
	 * @param atlas The atlas name
	 * @param sprite The sprite located on that atlas
	 * @return this
	 */
	public FluidSlot setBackground(ResourceLocation atlas, ResourceLocation sprite) {
		backgroundPair = Pair.of(atlas, sprite);
		return this;
	}
	
	public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
		return backgroundPair;
	}
	
}
