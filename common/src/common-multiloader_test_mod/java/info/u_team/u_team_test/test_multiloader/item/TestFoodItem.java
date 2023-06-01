package info.u_team.u_team_test.test_multiloader.item;

import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.item.food.UFoodPropertiesBuilder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class TestFoodItem extends UItem {
	
	private static final FoodProperties FOOD = UFoodPropertiesBuilder.builder().nutrition(4).saturationMod(1.2F).effect(() -> new MobEffectInstance(MobEffects.GLOWING, 200, 0), 1).alwaysEat().fast().build();
	
	public TestFoodItem() {
		super(new Properties().rarity(Rarity.RARE).food(FOOD));
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}
}
