package info.u_team.u_team_test.test_multiloader.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class TestEnchantment extends Enchantment {
	
	public TestEnchantment() {
		super(Rarity.COMMON, EnchantmentCategory.DIGGER, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
	}
	
	@Override
	public boolean isTreasureOnly() {
		return true;
	}
}
