package info.u_team.u_team_core.itemgroup;

import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;

public class UItemGroup extends ItemGroup {
	
	private IItemProvider provider;
	
	public UItemGroup(ResourceLocation location) {
		this(location.getNamespace(), location.getPath());
	}
	
	public UItemGroup(String modid, String name) {
		super(modid + "." + name);
	}
	
	public void setIcon(IItemProvider provider) {
		this.provider = provider;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public ItemStack createIcon() {
		return new ItemStack(provider);
	}
	
}
