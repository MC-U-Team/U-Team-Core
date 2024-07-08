package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.util.EnchantmentUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class ForgeEnchantmentUtilHandler implements EnchantmentUtil.Handler {
	
	@Override
	public int getEnchantmentLevel(Holder<Enchantment> enchantment, ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack);
	}
	
}
