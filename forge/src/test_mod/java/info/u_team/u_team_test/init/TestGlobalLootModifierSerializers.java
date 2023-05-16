package info.u_team.u_team_test.init;

import com.mojang.serialization.Codec;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.global_loot_modifier.AutoSmeltLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class TestGlobalLootModifierSerializers {
	
	public static final CommonRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = CommonRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, TestMod.MODID);
	
	public static final RegistryEntry<Codec<AutoSmeltLootModifier>> AUTO_SMELT = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("auto_smelt", () -> AutoSmeltLootModifier.CODEC);
	
	public static void register() {
		GLOBAL_LOOT_MODIFIER_SERIALIZERS.register();
	}
	
}
