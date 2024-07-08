package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

public class TestMultiLoaderEnchantments {
	
	public static final ResourceKey<Enchantment> TEST = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(TestMultiLoaderReference.MODID, "test"));
	
}
