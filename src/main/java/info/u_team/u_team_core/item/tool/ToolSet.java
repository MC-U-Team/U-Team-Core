package info.u_team.u_team_core.item.tool;

import java.util.Iterator;

import com.google.common.collect.Iterators;

import net.minecraft.world.item.TieredItem;
import net.minecraftforge.fmllegacy.RegistryObject;

public record ToolSet(RegistryObject<UAxeItem> axe, RegistryObject<UHoeItem> hoe, RegistryObject<UPickaxeItem> pickaxe, RegistryObject<UShovelItem> shovel, RegistryObject<USwordItem> sword) implements Iterable<RegistryObject<? extends TieredItem>> {
	
	@Override
	public Iterator<RegistryObject<? extends TieredItem>> iterator() {
		return Iterators.forArray(axe, hoe, pickaxe, shovel, sword);
	}
}
