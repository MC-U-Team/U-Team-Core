package info.u_team.u_team_core.item.armor;

import java.util.Iterator;

import com.google.common.collect.Iterators;

import net.minecraftforge.fmllegacy.RegistryObject;

public record ArmorSet(RegistryObject<UHelmetItem> helmet, RegistryObject<UChestplateItem> chestplate, RegistryObject<ULeggingsItem> leggings, RegistryObject<UBootsItem> boots) implements Iterable<RegistryObject<? extends UArmorItem>> {
	
	@Override
	public Iterator<RegistryObject<? extends UArmorItem>> iterator() {
		return Iterators.forArray(helmet, chestplate, leggings, boots);
	}
}
