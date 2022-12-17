package info.u_team.u_team_core.item;

import java.util.function.Supplier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

public class UBucketItem extends BucketItem {
	
	public UBucketItem(Properties properties, Supplier<? extends Fluid> fluid) {
		super(fluid, properties);
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag compound) {
		return new FluidBucketWrapper(stack);
	}
}
