package info.u_team.u_team_core.itemgroup;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UItemGroup extends CreativeModeTab {

	private final Supplier<? extends ItemLike> provider;

	public UItemGroup(ResourceLocation location, Supplier<? extends ItemLike> provider) {
		this(location.getNamespace(), location.getPath(), provider);
	}

	public UItemGroup(String modid, String name, Supplier<? extends ItemLike> provider) {
		super(modid + "." + name);
		this.provider = provider;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public ItemStack makeIcon() {
		return new ItemStack(provider.get());
	}

}
