package info.u_team.u_team_test.enchantment;

import net.minecraft.enchantment.*;
import net.minecraft.inventory.EquipmentSlotType;

public class AutoSmeltEnchantment extends Enchantment {
	
	public AutoSmeltEnchantment() {
		super(Rarity.COMMON, EnchantmentType.DIGGER, new EquipmentSlotType[] { EquipmentSlotType.MAINHAND });
	}
}
