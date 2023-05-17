package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.item.ExtendedTier;
import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.item.armor.ArmorSet;
import info.u_team.u_team_core.item.armor.ArmorSetCreator;
import info.u_team.u_team_core.item.armor.UArmorMaterialVanilla;
import info.u_team.u_team_core.item.tier.TierSet;
import info.u_team.u_team_core.item.tier.TierSetCreator;
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
	
	public static final TierSet TIER = TierSetCreator.create(ITEMS, "test", new Properties().fireResistant(), new ExtendedTier() {
		
		@Override
		public int getUses() {
			return 0;
		}
		
		@Override
		public float getSpeed() {
			return 0;
		}
		
		@Override
		public Ingredient getRepairIngredient() {
			return null;
		}
		
		@Override
		public int getLevel() {
			return 0;
		}
		
		@Override
		public int getEnchantmentValue() {
			return 0;
		}
		
		@Override
		public float getAttackDamageBonus() {
			return 0;
		}
		
		@Override
		public float getAttackSpeed(Tools tools) {
			return 0;
		}
		
		@Override
		public float getAttackDamage(Tools tools) {
			return 0;
		}
	});
	
	public static final ArmorMaterial ARMOR_MATERIAL = new UArmorMaterialVanilla(20, new int[] { 5, 6, 8, 2 }, 20, () -> SoundEvents.BEACON_ACTIVATE, 1, 1, () -> Ingredient.of(TEST.get()));
	public static final ArmorSet ARMOR = ArmorSetCreator.create(ITEMS, "test", new Properties().fireResistant(), ARMOR_MATERIAL);
	
	public static void register() {
		ITEMS.register();
	}
	
}
