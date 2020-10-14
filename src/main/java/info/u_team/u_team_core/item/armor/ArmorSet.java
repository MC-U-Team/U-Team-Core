package info.u_team.u_team_core.item.armor;

import java.util.Iterator;

import com.google.common.collect.Iterators;

import net.minecraftforge.fml.RegistryObject;

public class ArmorSet implements Iterable<RegistryObject<? extends UArmorItem>> {
	
	private final RegistryObject<UHelmetItem> helmet;
	private final RegistryObject<UChestplateItem> chestplate;
	private final RegistryObject<ULeggingsItem> leggings;
	private final RegistryObject<UBootsItem> boots;
	
	public ArmorSet(RegistryObject<UHelmetItem> helmet, RegistryObject<UChestplateItem> chestplate, RegistryObject<ULeggingsItem> leggings, RegistryObject<UBootsItem> boots) {
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
	}
	
	public RegistryObject<UHelmetItem> getHelmet() {
		return helmet;
	}
	
	public RegistryObject<UChestplateItem> getChestplate() {
		return chestplate;
	}
	
	public RegistryObject<ULeggingsItem> getLeggings() {
		return leggings;
	}
	
	public RegistryObject<UBootsItem> getBoots() {
		return boots;
	}
	
	@Override
	public Iterator<RegistryObject<? extends UArmorItem>> iterator() {
		return Iterators.forArray(helmet, chestplate, leggings, boots);
	}
}