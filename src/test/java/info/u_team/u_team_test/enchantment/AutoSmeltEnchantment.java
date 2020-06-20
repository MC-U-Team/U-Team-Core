package info.u_team.u_team_test.enchantment;

import info.u_team.u_team_core.enchantment.UEnchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class AutoSmeltEnchantment extends UEnchantment {
	
	public AutoSmeltEnchantment(String name) {
		super(name, Rarity.COMMON, EnchantmentType.DIGGER, new EquipmentSlotType[] { EquipmentSlotType.MAINHAND });
	}
}
