package info.u_team.u_team_core.item.armor;

import java.util.Iterator;

import com.google.common.collect.Iterators;

import info.u_team.u_team_core.api.registry.RegistryEntry;

public record ArmorSet(RegistryEntry<UHelmetItem> helmet, RegistryEntry<UChestplateItem> chestplate, RegistryEntry<ULeggingsItem> leggings, RegistryEntry<UBootsItem> boots) implements Iterable<RegistryEntry<? extends UArmorItem>> {
	
	@Override
	public Iterator<RegistryEntry<? extends UArmorItem>> iterator() {
		return Iterators.forArray(helmet, chestplate, leggings, boots);
	}
}
