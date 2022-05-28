package info.u_team.u_team_core.inventory;

import info.u_team.u_team_core.api.InteractionType;
import info.u_team.u_team_core.api.fluid.ExtendedFluidHandler;
import info.u_team.u_team_core.util.FluidHandlerHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;

public class UFluidStackHandler implements ExtendedFluidHandler, INBTSerializable<CompoundTag> {
	
	protected final NonNullList<FluidStack> stacks;
	
	protected final int capacity;
	
	public UFluidStackHandler(int size) {
		this(size, 64_000);
	}
	
	public UFluidStackHandler(int size, int capacity) {
		stacks = NonNullList.withSize(size, FluidStack.EMPTY);
		this.capacity = capacity;
	}
	
	@Override
	public int getTanks() {
		return stacks.size();
	}
	
	@Override
	public FluidStack getFluidInTank(int tank) {
		validateTankIndex(tank);
		return stacks.get(tank);
	}
	
	@Override
	public int getTankCapacity(int tank) {
		return capacity;
	}
	
	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		return true;
	}
	
	@Override
	public void setFluidInTank(int tank, FluidStack stack) {
		validateTankIndex(tank);
		stacks.set(tank, stack);
		onContentsChanged(tank);
	}
	
	@Override
	public FluidStack insertFluid(int tank, FluidStack stack, InteractionType action) {
		if (stack.isEmpty()) {
			return FluidStack.EMPTY;
		}
		
		if (!isFluidValid(tank, stack)) {
			return stack;
		}
		
		validateTankIndex(tank);
		
		final FluidStack existing = stacks.get(tank);
		
		int limit = getTankCapacity(tank);
		
		if (!existing.isEmpty()) {
			if (!FluidHandlerHelper.canFluidStacksStack(stack, existing)) {
				return stack;
			}
			
			limit -= existing.getAmount();
		}
		
		if (limit <= 0) {
			return stack;
		}
		
		final boolean reachedLimit = stack.getAmount() > limit;
		
		if (action.isExecute()) {
			if (existing.isEmpty()) {
				stacks.set(tank, reachedLimit ? FluidHandlerHelper.copyStackWithSize(stack, limit) : stack);
			} else {
				existing.grow(reachedLimit ? limit : stack.getAmount());
			}
			onContentsChanged(tank);
		}
		
		return reachedLimit ? FluidHandlerHelper.copyStackWithSize(stack, stack.getAmount() - limit) : FluidStack.EMPTY;
	}
	
	@Override
	public FluidStack extractFluid(int tank, int amount, InteractionType action) {
		if (amount == 0) {
			return FluidStack.EMPTY;
		}
		
		validateTankIndex(tank);
		
		final FluidStack existing = stacks.get(tank);
		
		if (existing.isEmpty()) {
			return FluidStack.EMPTY;
		}
		
		final int toExtract = amount;
		
		if (existing.getAmount() <= toExtract) {
			if (action.isExecute()) {
				stacks.set(tank, FluidStack.EMPTY);
				onContentsChanged(tank);
				return existing;
			} else {
				return existing.copy();
			}
		} else {
			if (action.isExecute()) {
				stacks.set(tank, FluidHandlerHelper.copyStackWithSize(existing, existing.getAmount() - toExtract));
				onContentsChanged(tank);
			}
			
			return FluidHandlerHelper.copyStackWithSize(existing, toExtract);
		}
	}
	
	@Override
	public CompoundTag serializeNBT() {
		final CompoundTag compound = new CompoundTag();
		final ListTag list = new ListTag();
		
		for (int index = 0; index < stacks.size(); index++) {
			final FluidStack fluidStack = stacks.get(index);
			if (!fluidStack.isEmpty()) {
				final CompoundTag slotCompound = new CompoundTag();
				slotCompound.putByte("Slot", (byte) index);
				fluidStack.writeToNBT(slotCompound);
				list.add(slotCompound);
			}
		}
		
		if (!list.isEmpty()) {
			compound.put("Fluids", list);
		}
		
		return compound;
	}
	
	@Override
	public void deserializeNBT(CompoundTag compound) {
		final ListTag list = compound.getList("Fluids", Tag.TAG_COMPOUND);
		for (int index = 0; index < list.size(); index++) {
			final CompoundTag slotCompound = list.getCompound(index);
			final int slot = slotCompound.getByte("Slot") & 255;
			if (slot >= 0 && slot < stacks.size()) {
				stacks.set(slot, FluidStack.loadFluidStackFromNBT(slotCompound));
			}
		}
		onLoad();
	}
	
	protected void validateTankIndex(int tank) {
		if (tank < 0 || tank >= stacks.size()) {
			throw new RuntimeException("Tank " + tank + " not in valid range - [0," + stacks.size() + ")");
		}
	}
	
	protected void onLoad() {
	}
	
	public void onContentsChanged(int tank) {
	}
}
