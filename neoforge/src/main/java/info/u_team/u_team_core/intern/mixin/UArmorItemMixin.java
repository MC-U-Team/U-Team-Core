package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;

import info.u_team.u_team_core.item.armor.UArmorItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

@Mixin(UArmorItem.class)
abstract class UArmorItemMixin extends ArmorItem {
	
	private UArmorItemMixin(ArmorMaterial material, Type type, Properties properties) {
		super(material, type, properties);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return ((UArmorItem) (Object) (this)).resolveArmorTexture(stack, entity, slot, type);
	}
	
}
