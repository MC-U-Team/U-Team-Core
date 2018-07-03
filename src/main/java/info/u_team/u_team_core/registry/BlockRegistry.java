package info.u_team.u_team_core.registry;

import static info.u_team.u_team_core.registry.ItemRegistry.items;

import java.util.*;

import info.u_team.u_team_core.api.registry.IUBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockRegistry {
	
	static List<Block> blocks = new ArrayList<>();
	
	public static void register(String modid, Block block) {
		if (block instanceof IUBlock) {
			IUBlock iublock = (IUBlock) block;
			block.setRegistryName(modid, iublock.getName());
			block.setUnlocalizedName(modid + ":" + iublock.getName());
			items.add(iublock.getItemBlock());
		} else {
			items.add(new ItemBlock(block));
		}
		blocks.add(block);
	}
	
	public static void register(String modid, Collection<Block> list) {
		list.forEach(block -> register(modid, block));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		blocks.forEach(registry::register);
	}
	
}
