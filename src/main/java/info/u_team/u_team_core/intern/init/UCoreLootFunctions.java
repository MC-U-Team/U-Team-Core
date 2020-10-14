package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.intern.loot.SetTileEntityNBTLootFunction;
import net.minecraft.block.Block;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.IEventBus;

public class UCoreLootFunctions {
	
	public static final LootFunctionType SET_TILEENTITY_NBT = new LootFunctionType(new SetTileEntityNBTLootFunction.Serializer());
	
	private static void registerLootFunction(Register<Block> event) {
		Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(UCoreMod.MODID, "set_tileentity_nbt"), SET_TILEENTITY_NBT);
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addGenericListener(Block.class, UCoreLootFunctions::registerLootFunction);
	}
}