package info.u_team.u_team_core.creativetab;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class UCreativeModeTab extends CreativeModeTab {
	
	private final Supplier<? extends ItemLike> provider;
	
	public UCreativeModeTab(ResourceLocation location, Supplier<? extends ItemLike> provider) {
		this(location.getNamespace(), location.getPath(), provider);
	}
	
	public UCreativeModeTab(String modid, String name, Supplier<? extends ItemLike> provider) {
		super(modid + "." + name);
		this.provider = provider;
	}
	
	@Override
	public ItemStack makeIcon() {
		return new ItemStack(provider.get());
	}
	
}
