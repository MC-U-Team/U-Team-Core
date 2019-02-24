package info.u_team.u_team_test.item;

import info.u_team.u_team_core.item.UItemFood;
import info.u_team.u_team_test.init.TestItemGroups;
import net.minecraft.init.MobEffects;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.api.distmarker.*;

public class ItemBasicFood extends UItemFood {
	
	public ItemBasicFood(String name) {
		super(name, TestItemGroups.group, new Properties().rarity(EnumRarity.RARE), 5, 8, false);
		setAlwaysEdible();
		setFastEating();
		setPotionEffect(new PotionEffect(MobEffects.GLOWING, 200, 0), 1);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
}
