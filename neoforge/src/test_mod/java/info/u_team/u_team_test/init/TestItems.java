package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.item.UBucketItem;
import info.u_team.u_team_core.item.armor.ArmorSet;
import info.u_team.u_team_core.item.armor.ArmorSetCreator;
import info.u_team.u_team_core.item.tier.TierSet;
import info.u_team.u_team_core.item.tier.TierSetCreator;
import info.u_team.u_team_test.TestMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class TestItems {
	
	public static final CommonRegister<Item> ITEMS = CommonRegister.create(Registries.ITEM, TestMod.MODID);
	
	public static final TierSet BASIC_TOOL = TierSetCreator.create(ITEMS, "basic_tool", new Properties().rarity(Rarity.UNCOMMON), TestTiers.BASIC);
	public static final ArmorSet BASIC_ARMOR = ArmorSetCreator.create(ITEMS, "basic_armor", new Properties().rarity(Rarity.RARE).fireResistant(), () -> TestArmorMaterials.BASIC.getHolder().get(), 10);
	
	public static final RegistryEntry<UBucketItem> TEST_FLUID_BUCKET = ITEMS.register("test_fluid_bucket", () -> new UBucketItem(new Properties().craftRemainder(Items.BUCKET).stacksTo(1), TestFluids.TEST_FLUID.get()));
	
	static void register() {
		ITEMS.register();
	}
}
