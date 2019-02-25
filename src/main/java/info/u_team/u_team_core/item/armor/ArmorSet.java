package info.u_team.u_team_core.item.armor;

import info.u_team.u_team_core.api.registry.IItemRegistryType;
import net.minecraft.item.Item;

public class ArmorSet implements IItemRegistryType {
	
	private final UItemHelmet helmet;
	private final UItemChestplate chestplate;
	private final UItemLeggings leggings;
	private final UItemBoots boots;
	
	public ArmorSet(UItemHelmet helmet, UItemChestplate chestplate, UItemLeggings leggings, UItemBoots boots) {
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
	}
	
	@Override
	public Item[] getItemArray() {
		return new Item[] { helmet, chestplate, leggings, boots };
	}
	
	public UItemHelmet getHelmet() {
		return helmet;
	}
	
	public UItemChestplate getChestplate() {
		return chestplate;
	}
	
	public UItemLeggings getLeggings() {
		return leggings;
	}
	
	public UItemBoots getBoots() {
		return boots;
	}
}
