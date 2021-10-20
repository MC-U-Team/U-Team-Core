package info.u_team.u_team_test.init;

import java.util.List;

import info.u_team.u_team_core.api.item.ExtendedTier;
import info.u_team.u_team_core.item.tier.UExtendedTier;
import info.u_team.u_team_test.TestMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class TestTiers {
	
	public static final ExtendedTier BASIC = new UExtendedTier(new float[] { 8, 0, 4, 2, 6 }, new float[] { -3.1F, -1, -2, -2, 0 }, BlockTags.createOptional(new ResourceLocation(TestMod.MODID, "needs_basic_tier")), 500, 10F, 8, 30, () -> Ingredient.of(TestItems.BASIC.get()));
	
	private static void setup(FMLCommonSetupEvent event) {
		TierSortingRegistry.registerTier(BASIC, new ResourceLocation(TestMod.MODID, "basic_tier"), List.of(Tiers.NETHERITE), List.of());
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(TestTiers::setup);
	}
}
