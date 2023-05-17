package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.item.ExtendedTier;
import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.item.armor.ArmorSet;
import info.u_team.u_team_core.item.armor.ArmorSetCreator;
import info.u_team.u_team_core.item.armor.UArmorMaterialVanilla;
import info.u_team.u_team_core.item.tier.TierSet;
import info.u_team.u_team_core.item.tier.TierSetCreator;
import info.u_team.u_team_core.item.tier.UExtendedTier;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;

public class TestMultiLoaderItems {
	
	public static final CommonRegister<Item> ITEMS = CommonRegister.create(Registries.ITEM, TestMultiLoaderReference.MODID);
	
	public static final RegistryEntry<Item> TEST = ITEMS.register("test_item", () -> new Item(new Properties()));
	
	public static final ExtendedTier EXTENDED_TIER = new UExtendedTier(new float[] { 8, 0, 4, 2, 6 }, new float[] { -3.1F, -1, -2, -2, 0 }, 2, 500, 10F, 8, 30, () -> Ingredient.of(TEST.get()));
	public static final TierSet TIER = TierSetCreator.create(ITEMS, "test", new Properties().fireResistant(), EXTENDED_TIER);
	
	public static final ArmorMaterial ARMOR_MATERIAL = new UArmorMaterialVanilla(20, new int[] { 5, 6, 8, 2 }, 20, () -> SoundEvents.BEACON_ACTIVATE, 1, 1, () -> Ingredient.of(TEST.get()));
	public static final ArmorSet ARMOR = ArmorSetCreator.create(ITEMS, "test", new Properties().fireResistant(), ARMOR_MATERIAL);
	
	public static void register() {
		ITEMS.register();
	}
	
}
