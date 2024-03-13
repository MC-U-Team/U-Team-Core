package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.global_loot_modifier.AutoSmeltLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class TestGlobalLootModifiers {
	
	public static final CommonDeferredRegister<GlobalLootModifierSerializer<?>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = CommonDeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, TestMod.MODID);
	
	public static final RegistryObject<AutoSmeltLootModifier.Serializer> AUTO_SMELT = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("auto_smelt", AutoSmeltLootModifier.Serializer::new);
	
	public static void register(IEventBus bus) {
		GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(bus);
	}
	
}
