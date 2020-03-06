package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.intern.loot.SetTileEntityNBTLootFunction;
import net.minecraft.block.Block;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = UCoreMain.MODID, bus = Bus.MOD)
public class UCoreLootTableRegistry {
	
	@SubscribeEvent
	public static void register(Register<Block> event) {
		LootFunctionManager.registerFunction(new SetTileEntityNBTLootFunction.Serializer());
	}
	
}
