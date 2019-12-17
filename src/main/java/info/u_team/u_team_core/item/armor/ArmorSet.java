package info.u_team.u_team_core.item.armor;

import info.u_team.u_team_core.api.registry.IUArrayRegistryType;
import net.minecraft.item.Item;

public class ArmorSet implements IUArrayRegistryType<Item> {
	
	private final UHelmetItem helmet;
	private final UChestplateItem chestplate;
	private final ULeggingsItem leggings;
	private final UBootsItem boots;
	
	public ArmorSet(UHelmetItem helmet, UChestplateItem chestplate, ULeggingsItem leggings, UBootsItem boots) {
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
	}
	
	@Override
	public Item[] getArray() {
		return new Item[] { helmet, chestplate, leggings, boots };
	}
	
	public UHelmetItem getHelmet() {
		return helmet;
	}
	
	public UChestplateItem getChestplate() {
		return chestplate;
	}
	
	public ULeggingsItem getLeggings() {
		return leggings;
	}
	
	public UBootsItem getBoots() {
		return boots;
	}
}
