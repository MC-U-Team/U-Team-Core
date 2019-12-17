package info.u_team.u_team_core.enchantment;

import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraft.enchantment.*;
import net.minecraft.inventory.EquipmentSlotType;

public class UEnchantment extends Enchantment implements IURegistryType {
	
	protected final String name;
	
	public UEnchantment(String name, Rarity rarity, EnchantmentType type, EquipmentSlotType[] slots) {
		super(rarity, type, slots);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
}
