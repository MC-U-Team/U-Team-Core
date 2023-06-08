package info.u_team.u_team_core.item.tier;

import java.util.Iterator;

import com.google.common.collect.Iterators;

import info.u_team.u_team_core.api.registry.RegistryEntry;
import net.minecraft.world.item.TieredItem;

public record TierSet(RegistryEntry<UAxeItem> axe, RegistryEntry<UHoeItem> hoe, RegistryEntry<UPickaxeItem> pickaxe, RegistryEntry<UShovelItem> shovel, RegistryEntry<USwordItem> sword) implements Iterable<RegistryEntry<? extends TieredItem>> {
	
	@Override
	public Iterator<RegistryEntry<? extends TieredItem>> iterator() {
		return Iterators.forArray(axe, hoe, pickaxe, shovel, sword);
	}
}
