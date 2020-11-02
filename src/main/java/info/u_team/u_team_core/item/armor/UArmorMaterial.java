package info.u_team.u_team_core.item.armor;

import java.util.function.Supplier;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;

public class UArmorMaterial implements IArmorMaterial {
	
	private final int[] durability;
	private final int[] armorPoints;
	private final int enchantability;
	private final LazyValue<SoundEvent> soundEvent;
	private final float toughness;
	private final float knockbackResistance;
	private final LazyValue<Ingredient> repair;
	
	public UArmorMaterial(int[] durability, int[] armorPoints, int enchantability, Supplier<SoundEvent> soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> ingredient) {
		this.durability = durability;
		this.armorPoints = armorPoints;
		this.enchantability = enchantability;
		this.soundEvent = new LazyValue<>(soundEvent);
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repair = new LazyValue<>(ingredient);
	}
	
	@Override
	public int getDurability(EquipmentSlotType slot) {
		return durability[slot.getIndex()];
	}
	
	@Override
	public int getDamageReductionAmount(EquipmentSlotType slot) {
		return armorPoints[slot.getIndex()];
	}
	
	@Override
	public int getEnchantability() {
		return enchantability;
	}
	
	@Override
	public SoundEvent getSoundEvent() {
		return soundEvent.getValue();
	}
	
	@Override
	public Ingredient getRepairMaterial() {
		return repair.getValue();
	}
	
	@Override
	public float getToughness() {
		return toughness;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return "invalid";
	}
	
	@Override
	public float getKnockbackResistance() {
		return knockbackResistance;
	}
	
}
