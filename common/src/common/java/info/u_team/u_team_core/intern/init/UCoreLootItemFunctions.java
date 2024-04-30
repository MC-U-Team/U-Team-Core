package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreReference;
import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.intern.loot_item_function.SetBlockEntityNBTLootItemFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class UCoreLootItemFunctions {
	
	public static final CommonRegister<LootItemFunctionType<?>> LOOT_FUNCTIONS = CommonRegister.create(Registries.LOOT_FUNCTION_TYPE, UCoreReference.MODID);
	
	public static final RegistryEntry<LootItemFunctionType<SetBlockEntityNBTLootItemFunction>> SET_BLOCKENTITY_NBT = LOOT_FUNCTIONS.register("set_blockentity_nbt", () -> new LootItemFunctionType<>(SetBlockEntityNBTLootItemFunction.CODEC));
	
	static void register() {
		LOOT_FUNCTIONS.register();
	}
}
