package info.u_team.u_team_core.item.armor;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public interface UArmorItemMixinExtension {
	
	String resolveArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type);
}