package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.item.ExtendedTier;
import info.u_team.u_team_core.item.tier.UExtendedTier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;

public class TestToolMaterial {
	
	public static final ExtendedTier BASIC = new UExtendedTier(new float[] { 8, 0, 4, 2, 6 }, new float[] { -3.1F, -1F, -2F, -2F, 0F }, BlockTags.NEEDS_DIAMOND_TOOL, 500, 5F, 8, 30, () -> Ingredient.of(TestItems.BASIC.get()));
}
