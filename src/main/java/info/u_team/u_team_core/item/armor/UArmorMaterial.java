package info.u_team.u_team_core.item.armor;

import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UArmorMaterial implements ArmorMaterial {
	
	private final int[] durability;
	private final int[] armorPoints;
	private final int enchantability;
	private final LazyLoadedValue<SoundEvent> soundEvent;
	private final float toughness;
	private final float knockbackResistance;
	private final LazyLoadedValue<Ingredient> repair;
	
	public UArmorMaterial(int[] durability, int[] armorPoints, int enchantability, Supplier<SoundEvent> soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> ingredient) {
		this.durability = durability;
		this.armorPoints = armorPoints;
		this.enchantability = enchantability;
		this.soundEvent = new LazyLoadedValue<>(soundEvent);
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repair = new LazyLoadedValue<>(ingredient);
	}
	
	@Override
	public int getDurabilityForSlot(EquipmentSlot slot) {
		return durability[slot.getIndex()];
	}
	
	@Override
	public int getDefenseForSlot(EquipmentSlot slot) {
		return armorPoints[slot.getIndex()];
	}
	
	@Override
	public int getEnchantmentValue() {
		return enchantability;
	}
	
	@Override
	public SoundEvent getEquipSound() {
		return soundEvent.get();
	}
	
	@Override
	public Ingredient getRepairIngredient() {
		return repair.get();
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
