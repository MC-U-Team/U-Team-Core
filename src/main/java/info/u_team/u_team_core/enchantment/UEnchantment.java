package info.u_team.u_team_core.enchantment;

import info.u_team.u_team_core.api.registry.IUEnchantment;
import net.minecraft.enchantment.*;
import net.minecraft.inventory.EntityEquipmentSlot;

public class UEnchantment extends Enchantment implements IUEnchantment {
	
	protected final String name;
	
	public UEnchantment(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot[] slots) {
		super(rarity, type, slots);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}
