package info.u_team.u_team_test.test_multiloader.forge.init;

import com.mojang.serialization.MapCodec;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import info.u_team.u_team_test.test_multiloader.forge.global_loot_modifier.TestLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeTestMultiLoaderGlobalLootModifierSerializers {
	
	public static final CommonRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = CommonRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, TestMultiLoaderReference.MODID);
	
	public static final RegistryEntry<MapCodec<TestLootModifier>> TEST = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("test", () -> TestLootModifier.CODEC);
	
	static void register() {
		GLOBAL_LOOT_MODIFIER_SERIALIZERS.register();
	}
	
}
