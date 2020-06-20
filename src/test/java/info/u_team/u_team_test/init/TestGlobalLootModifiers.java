package info.u_team.u_team_test.init;

import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.global_loot_modifier.AutoSmeltLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestGlobalLootModifiers {
	
	@SubscribeEvent
	public static void register(Register<GlobalLootModifierSerializer<?>> event) {
		event.getRegistry().register(new AutoSmeltLootModifier.Serializer().setRegistryName(TestMod.MODID, "auto_smelt"));
	}
	
}
