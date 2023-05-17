package info.u_team.u_team_core.item.tier;

import java.util.function.Supplier;

import info.u_team.u_team_core.api.item.ExtendedTier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class UForgeExtendedTier implements ExtendedTier {
	
	private final ExtendedTier tier;
	private final TagKey<Block> tag;
	
	public UForgeExtendedTier(float[] attackDamage, float[] attackSpeed, TagKey<Block> tag, int uses, float speed, float attackDamageBonus, int enchantmentValue, Supplier<? extends Ingredient> repairIngredient) {
		this(new UExtendedTier(attackDamage, attackSpeed, 0, uses, speed, attackDamageBonus, enchantmentValue, repairIngredient), tag);
	}
	
	public UForgeExtendedTier(ExtendedTier tier, TagKey<Block> tag) {
		this.tier = tier;
		this.tag = tag;
	}
	
	@Override
	public int getUses() {
		return tier.getUses();
	}
	
	@Override
	public float getSpeed() {
		return tier.getSpeed();
	}
	
	@Override
	public float getAttackDamageBonus() {
		return tier.getAttackDamageBonus();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int getLevel() {
		return tier.getLevel();
	}
	
	@Override
	public TagKey<Block> getTag() {
		return tag;
	}
	
	@Override
	public int getEnchantmentValue() {
		return tier.getEnchantmentValue();
	}
	
	@Override
	public Ingredient getRepairIngredient() {
		return tier.getRepairIngredient();
	}
	
	@Override
	public float getAttackDamage(Tools tools) {
		return tier.getAttackDamage(tools);
	}
	
	@Override
	public float getAttackSpeed(Tools tools) {
		return tier.getAttackSpeed(tools);
	}
}
