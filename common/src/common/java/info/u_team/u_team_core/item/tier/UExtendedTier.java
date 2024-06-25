package info.u_team.u_team_core.item.tier;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import info.u_team.u_team_core.api.item.ExtendedTier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class UExtendedTier implements ExtendedTier {
	
	private final Map<Tools, Float> attackDamage;
	private final Map<Tools, Float> attackSpeed;
	private final TagKey<Block> incorrectBlocksForDrops;
	private final int uses;
	private final float speed;
	private final float attackDamageBonus;
	private final int enchantmentValue;
	private final Supplier<Ingredient> repairIngredient;
	
	public UExtendedTier(Map<Tools, Float> attackDamage, Map<Tools, Float> attackSpeed, TagKey<Block> incorrectBlocksForDrops, int uses, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
		this.attackDamage = new EnumMap<>(Tools.class);
		this.attackDamage.putAll(attackDamage);
		this.attackSpeed = new EnumMap<>(Tools.class);
		this.attackSpeed.putAll(attackSpeed);
		this.incorrectBlocksForDrops = incorrectBlocksForDrops;
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
	public TagKey<Block> getIncorrectBlocksForDrops() {
		return incorrectBlocksForDrops;
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
		return attackDamage.getOrDefault(tools, -1000F);
	}
	
	@Override
	public float getAttackSpeed(Tools tools) {
		return attackSpeed.getOrDefault(tools, -1000F);
	}
	
}
