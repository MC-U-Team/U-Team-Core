package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.intern.loot.SetBlockEntityNBTLootFunction;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class UCoreLootFunctions {
	
	public static final CommonDeferredRegister<LootItemFunctionType> LOOT_FUNCTIONS = CommonDeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, UCoreMod.MODID);
	
	public static final RegistryObject<LootItemFunctionType> SET_BLOCKENTITY_NBT = LOOT_FUNCTIONS.register("set_blockentity_nbt", () -> new LootItemFunctionType(new SetBlockEntityNBTLootFunction.Serializer()));
	
	public static void registerMod(IEventBus bus) {
		LOOT_FUNCTIONS.register(bus);
	}
}
