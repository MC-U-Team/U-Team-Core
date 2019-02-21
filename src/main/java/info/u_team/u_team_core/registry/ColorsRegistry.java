package info.u_team.u_team_core.registry;

import java.util.*;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

import info.u_team.u_team_core.UCoreMain;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.*;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = UCoreMain.modid, bus = Bus.MOD)
public class ColorsRegistry {
	
	static List<Pair<IBlockColor, Block[]>> blocks = new ArrayList<>();
	static List<Pair<IItemColor, IItemProvider[]>> items = new ArrayList<>();
	
	public static void register(IBlockColor color, Block block, Block... moreBlocks) {
		blocks.add(Pair.of(color, ArrayUtils.add(moreBlocks, block)));
	}
	
	public static void register(IItemColor color, IItemProvider item, IItemProvider... moreItems) {
		items.add(Pair.of(color, ArrayUtils.add(moreItems, item)));
	}
	
	@SubscribeEvent
	public static void event(ColorHandlerEvent.Item event) {
		BlockColors blockcolors = event.getBlockColors();
		ItemColors itemcolors = event.getItemColors();
		blocks.forEach(pair -> blockcolors.register(pair.getKey(), pair.getValue()));
		items.forEach(pair -> itemcolors.register(pair.getKey(), pair.getValue()));
	}
}
