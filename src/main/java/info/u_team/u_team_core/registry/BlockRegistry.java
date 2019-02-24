package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.api.registry.IUBlock;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = UCoreMain.modid, bus = Bus.MOD)
public class BlockRegistry {
	
	static List<Block> blocks = new ArrayList<>();
	
	public static void register(String modid, Block block) {
		if (block instanceof IUBlock) {
			IUBlock ublock = (IUBlock) block;
			block.setRegistryName(modid, ublock.getEntryName());
			
			ItemBlock itemblock = ublock.getItemBlock();
			if (itemblock != null) {
				itemblock.setRegistryName(block.getRegistryName());
				ItemRegistry.items.add(itemblock);
			}
		}
		blocks.add(block);
	}
	
	public static void register(String modid, Collection<Block> list) {
		list.forEach(block -> register(modid, block));
	}
	
	public static void register(String modid, Class<?> clazz) {
		register(modid, RegistryUtil.getRegistryEntries(Block.class, clazz));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		blocks.forEach(registry::register);
	}
}
