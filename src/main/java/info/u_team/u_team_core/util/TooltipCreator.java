package info.u_team.u_team_core.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TooltipCreator {
	
	private static final Object[] EMPTY = new Object[0];
	
	public static MutableComponent create(Item item, String category, int line) {
		return create(item, category, line, EMPTY);
	}
	
	public static MutableComponent create(Item item, String category, int line, Object... args) {
		if (!category.isEmpty()) {
			category += ".";
		}
		return Component.translatable(item.getDescriptionId() + ".tooltip." + category + line, args);
	}
	
	public static MutableComponent create(Block block, String category, int line) {
		return create(block, category, line, EMPTY);
	}
	
	public static MutableComponent create(Block block, String category, int line, Object... args) {
		if (!category.isEmpty()) {
			category += ".";
		}
		return Component.translatable(block.getDescriptionId() + ".tooltip." + category + line, args);
	}
	
	public static MutableComponent create(String modid, String key, String category, int line) {
		return create(modid, key, category, line, EMPTY);
	}
	
	public static MutableComponent create(String modid, String key, String category, int line, Object... args) {
		if (!category.isEmpty()) {
			category += ".";
		}
		return Component.translatable("general." + modid + "." + key + ".tooltip." + category + line, args);
	}
	
}
