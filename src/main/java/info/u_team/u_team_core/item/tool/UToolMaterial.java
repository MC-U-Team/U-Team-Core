package info.u_team.u_team_core.item.tool;

import java.util.function.Supplier;

import info.u_team.u_team_core.api.IToolMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;

public class UToolMaterial implements IToolMaterial {
	
	private final int[] additionaldamage;
	private final float[] speed;
	private final int harvestlevel;
	private final int durability;
	private final float efficiency;
	private final float basedamage;
	private final int enchantability;
	private final LazyLoadBase<Ingredient> repair;
	
	public UToolMaterial(int[] additionaldamage, float[] speed, int harvestlevel, int durability, float efficiency, float basedamage, int enchantability, Supplier<Ingredient> ingredient) {
		this.additionaldamage = additionaldamage;
		this.speed = speed;
		this.harvestlevel = harvestlevel;
		this.durability = durability;
		this.efficiency = efficiency;
		this.basedamage = basedamage;
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
		return basedamage;
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
	public int getAdditionalDamage(Tools tools) {
		return additionaldamage[tools.getIndex()];
	}
	
	@Override
	public float getAttackSpeed(Tools tools) {
		return speed[tools.getIndex()];
	}
	
}
