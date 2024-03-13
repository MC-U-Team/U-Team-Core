package info.u_team.u_team_test.item;

import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_test.init.TestItemGroups;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraftforge.api.distmarker.*;

public class BasicFoodItem extends UItem {
	
	private static final Food FOOD = (new Food.Builder()).hunger(4).saturation(1.2F).effect(() -> new EffectInstance(Effects.GLOWING, 200, 0), 1).setAlwaysEdible().fastToEat().build();
	
	public BasicFoodItem() {
		super(TestItemGroups.GROUP, new Properties().rarity(Rarity.RARE).food(FOOD));
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
}
