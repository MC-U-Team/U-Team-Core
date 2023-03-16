package info.u_team.u_team_test.init;

import info.u_team.u_team_test.TestMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class TestDamageSources {
	
	public static final ResourceKey<DamageType> RADIATION = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(TestMod.MODID, "radiation"));
	
}
