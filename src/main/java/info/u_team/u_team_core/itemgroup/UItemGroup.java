package info.u_team.u_team_core.itemgroup;

import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;

public class UItemGroup extends ItemGroup {
	
	private IItemProvider provider;
	
	public UItemGroup(String modid, String name) {
		this(new ResourceLocation(modid, name));
	}
	
	public UItemGroup(ResourceLocation location) {
		super(location.toString());
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
