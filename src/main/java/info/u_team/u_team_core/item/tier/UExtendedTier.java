package info.u_team.u_team_core.item.tier;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import info.u_team.u_team_core.api.item.ExtendedTier;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class UExtendedTier implements ExtendedTier {
	
	private final float[] attackDamage;
	private final float[] attackSpeed;
	private final Tag<Block> tag;
	private final int uses;
	private final float speed;
	private final float attackDamageBonus;
	private final int enchantmentValue;
	private final Supplier<? extends Ingredient> repairIngredient;
	
	public UExtendedTier(float[] attackDamage, float[] attackSpeed, Tag<Block> tag, int uses, float speed, float attackDamageBonus, int enchantmentValue, Supplier<? extends Ingredient> repairIngredient) {
		this.attackDamage = attackDamage;
		this.attackSpeed = attackSpeed;
		this.tag = tag;
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
		return 0;
	}
	
	@Override
	public Tag<Block> getTag() {
		return tag;
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
