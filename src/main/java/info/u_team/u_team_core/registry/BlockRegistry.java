package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.api.registry.IUBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = UCoreMain.modid)
public class BlockRegistry {
	
	static List<Block> blocks = new ArrayList<>();
	
	public static void register(String modid, Block block) {
		if (block instanceof IUBlock) {
			IUBlock iublock = (IUBlock) block;
			block.setRegistryName(modid, iublock.getEntryName());
			
			ItemBlock itemblock = iublock.getItemBlock();
			itemblock.setRegistryName(block.getRegistryName());
			if (itemblock != null) {
				ItemRegistry.items.add(itemblock);
			}
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
