package info.u_team.u_team_test.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class AutoSmeltEnchantment extends Enchantment {
	
	public AutoSmeltEnchantment() {
		super(Rarity.COMMON, EnchantmentCategory.DIGGER, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
	}
}
