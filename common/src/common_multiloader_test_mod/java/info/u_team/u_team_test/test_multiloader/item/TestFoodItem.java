package info.u_team.u_team_test.test_multiloader.item;

import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.item.food.UFoodPropertiesBuilder;
import info.u_team.u_team_core.util.EnchantmentUtil;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class TestFoodItem extends UItem {
	
	private static final FoodProperties FOOD = UFoodPropertiesBuilder.builder().nutrition(4).saturationModifier(1.2F).effect(new MobEffectInstance(MobEffects.GLOWING, 200, 0), 1).alwaysEdible().fast().build();
	
	public TestFoodItem() {
		super(new Properties().rarity(Rarity.RARE).food(FOOD));
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
		if (livingEntity instanceof final ServerPlayer player) {
			final int enchantment = EnchantmentUtil.getEnchantmentLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING), stack);
			if (enchantment > 0) {
				final ItemEntity itemEntity = player.drop(new ItemStack(RegistryUtil.getBuiltInRegistry(Registries.ITEM).getRandom(player.getRandom()).map(reference -> reference.value()).orElse(Items.STONE), enchantment * 5), true);
				if (itemEntity != null) {
					itemEntity.setDefaultPickUpDelay();
					itemEntity.setExtendedLifetime();
				}
			}
		}
		return super.finishUsingItem(stack, level, livingEntity);
	}
}
