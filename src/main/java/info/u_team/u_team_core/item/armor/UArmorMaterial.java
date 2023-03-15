package info.u_team.u_team_core.item.armor;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class UArmorMaterial implements ArmorMaterial {
	
	private final int[] durability;
	private final int[] armorPoints;
	private final int enchantability;
	private final Supplier<SoundEvent> soundEvent;
	private final float toughness;
	private final float knockbackResistance;
	private final Supplier<Ingredient> repair;
	
	// TODO change contructor to use armor item types instead of arrays
	public UArmorMaterial(int[] durability, int[] armorPoints, int enchantability, Supplier<SoundEvent> soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> ingredient) {
		this.durability = durability;
		this.armorPoints = armorPoints;
		this.enchantability = enchantability;
		this.soundEvent = Suppliers.memoize(soundEvent::get);
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		repair = Suppliers.memoize(ingredient::get);
	}
	
	@Override
	public int getDurabilityForType(ArmorItem.Type type) {
		return durability[type.getSlot().getIndex()];
	}
	
	@Override
	public int getDefenseForType(ArmorItem.Type type) {
		return armorPoints[type.getSlot().getIndex()];
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
	public String getName() {
		return "invalid";
	}
	
	@Override
	public float getKnockbackResistance() {
		return knockbackResistance;
	}
	
}
