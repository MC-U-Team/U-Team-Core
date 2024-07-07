package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class TestMultiLoaderDamageSources {
	
	public static final ResourceKey<DamageType> TEST = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(TestMultiLoaderReference.MODID, "test"));
	
}
