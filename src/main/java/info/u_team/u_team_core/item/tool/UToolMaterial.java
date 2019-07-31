package info.u_team.u_team_core.item.tool;

import java.util.function.Supplier;

import info.u_team.u_team_core.api.IToolMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;

public class UToolMaterial implements IToolMaterial {
	
	private final float[] additionalDamage;
	private final float[] speed;
	private final int harvestlevel;
	private final int durability;
	private final float efficiency;
	private final float baseDamage;
	private final int enchantability;
	private final LazyLoadBase<Ingredient> repair;
	
	public UToolMaterial(float[] additionalDamage, float[] speed, int harvestlevel, int durability, float efficiency, float baseDamage, int enchantability, Supplier<Ingredient> ingredient) {
		this.additionalDamage = additionalDamage;
		this.speed = speed;
		this.harvestlevel = harvestlevel;
		this.durability = durability;
		this.efficiency = efficiency;
		this.baseDamage = baseDamage;
		this.enchantability = enchantability;
		this.repair = new LazyLoadBase<>(ingredient);
	}
	
	@Override
	public int getMaxUses() {
		return durability;
	}
	
	@Override
	public float getEfficiency() {
		return efficiency;
	}
	
	@Override
	public float getAttackDamage() {
		return baseDamage;
	}
	
	@Override
	public int getHarvestLevel() {
		return harvestlevel;
	}
	
	@Override
	public int getEnchantability() {
		return enchantability;
	}
	
	@Override
	public Ingredient getRepairMaterial() {
		return repair.getValue();
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
