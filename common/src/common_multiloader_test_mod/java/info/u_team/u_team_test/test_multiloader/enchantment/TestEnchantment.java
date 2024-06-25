package info.u_team.u_team_test.test_multiloader.enchantment;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class TestEnchantment extends Enchantment {
	
	public TestEnchantment() {
		super(Enchantment.definition(ItemTags.MINING_ENCHANTABLE, 1, 1, Enchantment.dynamicCost(1, 11), Enchantment.dynamicCost(12, 11), 1, EquipmentSlot.MAINHAND));
	}
	
	@Override
	public boolean isTreasureOnly() {
		return true;
	}
}
