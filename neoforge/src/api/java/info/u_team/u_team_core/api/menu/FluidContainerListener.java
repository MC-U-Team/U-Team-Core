package info.u_team.u_team_core.api.menu;

import info.u_team.u_team_core.menu.FluidContainerMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public interface FluidContainerListener extends ContainerListener {
	
	@Override
	void slotChanged(AbstractContainerMenu menu, int slotId, ItemStack stack);
	
	@Override
	void dataChanged(AbstractContainerMenu menu, int slotId, int value);
	
	void fluidSlotChanged(FluidContainerMenu menu, int slotId, FluidStack stack);
	
}
