package info.u_team.u_team_core.item.tier;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import info.u_team.u_team_core.api.item.ExtendedTier;
import net.minecraft.world.item.crafting.Ingredient;

public class UExtendedTier implements ExtendedTier {
	
	private final float[] attackDamage;
	private final float[] attackSpeed;
	private final int level;
	private final int uses;
	private final float speed;
	private final float attackDamageBonus;
	private final int enchantmentValue;
	private final Supplier<? extends Ingredient> repairIngredient;
	
	public UExtendedTier(float[] attackDamage, float[] attackSpeed, int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, Supplier<? extends Ingredient> repairIngredient) {
		this.attackDamage = attackDamage;
		this.attackSpeed = attackSpeed;
		this.level = level;
		this.uses = uses;
		this.speed = speed;
		this.attackDamageBonus = attackDamageBonus;
		this.enchantmentValue = enchantmentValue;
		this.repairIngredient = Suppliers.memoize(repairIngredient::get);
	}
	
	@Override
	public int getUses() {
		return uses;
	}
	
	@Override
	public float getSpeed() {
		return speed;
	}
	
	@Override
	public float getAttackDamageBonus() {
		return attackDamageBonus;
	}
	
	@Override
	public int getLevel() {
		return level;
	}
	
	@Override
	public int getEnchantmentValue() {
		return enchantmentValue;
	}
	
	@Override
	public Ingredient getRepairIngredient() {
		return repairIngredient.get();
	}
	
	@Override
	public float getAttackDamage(Tools tools) {
		return attackDamage[tools.getIndex()];
	}
	
	@Override
	public float getAttackSpeed(Tools tools) {
		return attackSpeed[tools.getIndex()];
	}
	
}
