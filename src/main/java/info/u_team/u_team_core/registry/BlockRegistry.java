package info.u_team.u_team_core.registry;

import static info.u_team.u_team_core.registry.ItemRegistry.items;

import java.util.*;

import info.u_team.u_team_core.block.IUBlock;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockRegistry {
	
	static List<Block> blocks = new ArrayList<>();
	
	public static <T extends Block & IUBlock> void register(String modid, T block) {
		block.setRegistryName(modid, block.getName());
		blocks.add(block);
		items.add(block.getItemBlock());
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		blocks.forEach(registry::register);
	}
	
}
