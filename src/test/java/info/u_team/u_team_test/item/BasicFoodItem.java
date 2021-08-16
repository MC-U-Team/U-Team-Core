package info.u_team.u_team_test.item;

import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_test.init.TestItemGroups;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class BasicFoodItem extends UItem {
	
	private static final FoodProperties FOOD = new FoodProperties.Builder().nutrition(4).saturationMod(1.2F).effect(() -> new MobEffectInstance(MobEffects.GLOWING, 200, 0), 1).alwaysEat().fast().build();
	
	public BasicFoodItem() {
		super(TestItemGroups.GROUP, new Properties().rarity(Rarity.RARE).food(FOOD));
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}
}
