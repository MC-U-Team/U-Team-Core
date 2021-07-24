package info.u_team.u_team_core.itemgroup;

import java.util.function.Supplier;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UItemGroup extends ItemGroup {
	
	private final Supplier<? extends IItemProvider> provider;
	
	public UItemGroup(ResourceLocation location, Supplier<? extends IItemProvider> provider) {
		this(location.getNamespace(), location.getPath(), provider);
	}
	
	public UItemGroup(String modid, String name, Supplier<? extends IItemProvider> provider) {
		super(modid + "." + name);
		this.provider = provider;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public ItemStack makeIcon() {
		return new ItemStack(provider.get());
	}
	
}
