package info.u_team.u_team_test.enchantment;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.world.item.enchantment.Enchantment;

public class AutoSmeltEnchantment extends Enchantment {
	
	public AutoSmeltEnchantment() {
		super(Rarity.COMMON, EnchantmentType.DIGGER, new EquipmentSlotType[] { EquipmentSlotType.MAINHAND });
	}
}
