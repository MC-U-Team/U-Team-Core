package info.u_team.u_team_core.util;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentUtil {
	
	private static final Handler HANDLER = ServiceUtil.loadOne(Handler.class);
	
	public static int getEnchantmentLevel(Holder<Enchantment> enchantment, ItemStack stack) {
		return HANDLER.getEnchantmentLevel(enchantment, stack);
	}
	
	public static interface Handler {
		
		int getEnchantmentLevel(Holder<Enchantment> enchantment, ItemStack stack);
	}
	
}
