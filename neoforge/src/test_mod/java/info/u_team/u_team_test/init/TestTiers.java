package info.u_team.u_team_test.init;

import java.util.List;

import info.u_team.u_team_core.api.item.ExtendedTier;
import info.u_team.u_team_core.item.tier.UNeoForgeExtendedTier;
import info.u_team.u_team_core.util.TagUtil;
import info.u_team.u_team_test.TestMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.TierSortingRegistry;

public class TestTiers {
	
	public static final ExtendedTier BASIC = new UNeoForgeExtendedTier(new float[] { 8, 0, 4, 2, 6 }, new float[] { -3.1F, -1, -2, -2, 0 }, TagUtil.createBlockTag(TestMod.MODID, "needs_basic_tier"), 500, 10F, 8, 30, () -> Ingredient.of(Items.ZOMBIE_HEAD));
	
	private static void setup(FMLCommonSetupEvent event) {
		TierSortingRegistry.registerTier(BASIC, new ResourceLocation(TestMod.MODID, "basic_tier"), List.of(Tiers.NETHERITE), List.of());
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(TestTiers::setup);
	}
}
