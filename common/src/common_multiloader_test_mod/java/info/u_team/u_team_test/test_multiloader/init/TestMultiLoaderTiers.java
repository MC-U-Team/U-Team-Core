package info.u_team.u_team_test.test_multiloader.init;

import java.util.EnumMap;

import info.u_team.u_team_core.api.item.ExtendedTier;
import info.u_team.u_team_core.api.item.ExtendedTier.Tools;
import info.u_team.u_team_core.item.tier.UExtendedTier;
import net.minecraft.Util;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;

public class TestMultiLoaderTiers {
	
	public static final ExtendedTier TEST = new UExtendedTier(Util.make(new EnumMap<>(Tools.class), map -> {
		map.put(Tools.AXE, 8F);
		map.put(Tools.HOE, 0F);
		map.put(Tools.PICKAXE, 4F);
		map.put(Tools.SHOVEL, 2F);
		map.put(Tools.SWORD, 6F);
	}), Util.make(new EnumMap<>(Tools.class), map -> {
		map.put(Tools.AXE, -3.1F);
		map.put(Tools.HOE, -1F);
		map.put(Tools.PICKAXE, -2F);
		map.put(Tools.SHOVEL, -2F);
		map.put(Tools.SWORD, 0F);
	}), BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 500, 10F, 8, 30, () -> Ingredient.of(TestMultiLoaderItems.TEST.get()));
	
}
