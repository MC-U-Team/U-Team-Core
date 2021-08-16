package info.u_team.u_team_core.item.tool;

import java.util.function.Supplier;

import info.u_team.u_team_core.api.IToolMaterial;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.crafting.Ingredient;

public class UToolMaterial implements IToolMaterial {
	
	private final float[] additionalDamage;
	private final float[] speed;
	private final int harvestlevel;
	private final int durability;
	private final float efficiency;
	private final float baseDamage;
	private final int enchantability;
	private final LazyLoadedValue<Ingredient> repair;
	
	public UToolMaterial(float[] additionalDamage, float[] speed, int harvestlevel, int durability, float efficiency, float baseDamage, int enchantability, Supplier<Ingredient> ingredient) {
		this.additionalDamage = additionalDamage;
		this.speed = speed;
		this.harvestlevel = harvestlevel;
		this.durability = durability;
		this.efficiency = efficiency;
		this.baseDamage = baseDamage;
		this.enchantability = enchantability;
		repair = new LazyLoadedValue<>(ingredient);
	}
	
	@Override
	public int getUses() {
		return durability;
	}
	
	@Override
	public float getSpeed() {
		return efficiency;
	}
	
	@Override
	public float getAttackDamageBonus() {
		return baseDamage;
	}
	
	@Override
	public int getLevel() {
		return harvestlevel;
	}
	
	@Override
	public int getEnchantmentValue() {
		return enchantability;
	}
	
	@Override
	public Ingredient getRepairIngredient() {
		return repair.get();
	}
	
	@Override
	public float getAdditionalDamage(Tools tools) {
		return additionalDamage[tools.getIndex()];
	}
	
	@Override
	public float getAttackSpeed(Tools tools) {
		return speed[tools.getIndex()];
	}
	
}
