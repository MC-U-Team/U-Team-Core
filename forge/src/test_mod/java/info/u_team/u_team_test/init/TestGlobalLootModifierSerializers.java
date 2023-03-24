package info.u_team.u_team_test.init;

import com.mojang.serialization.Codec;

import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.global_loot_modifier.AutoSmeltLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TestGlobalLootModifierSerializers {
	
	public static final CommonDeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = CommonDeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, TestMod.MODID);
	
	public static final RegistryObject<Codec<AutoSmeltLootModifier>> AUTO_SMELT = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("auto_smelt", () -> AutoSmeltLootModifier.CODEC);
	
	public static void registerMod(IEventBus bus) {
		GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(bus);
	}
	
}
