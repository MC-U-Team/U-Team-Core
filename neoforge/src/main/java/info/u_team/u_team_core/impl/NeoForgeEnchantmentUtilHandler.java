package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.util.EnchantmentUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class NeoForgeEnchantmentUtilHandler implements EnchantmentUtil.Handler {
	
	@Override
	public int getEnchantmentLevel(Enchantment enchantment, ItemStack stack) {
		return stack.getEnchantmentLevel(enchantment);
	}
	
}
