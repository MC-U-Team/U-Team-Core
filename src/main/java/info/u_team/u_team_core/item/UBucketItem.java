package info.u_team.u_team_core.item;

import java.util.function.Supplier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

public class UBucketItem extends BucketItem {

	public UBucketItem(Properties properties, Supplier<? extends Fluid> fluid) {
		this(null, properties, fluid);
	}

	public UBucketItem(CreativeModeTab group, Properties properties, Supplier<? extends Fluid> fluid) {
		super(fluid, group == null ? properties : properties.tab(group));
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag compound) {
		return new FluidBucketWrapper(stack);
	}
}
