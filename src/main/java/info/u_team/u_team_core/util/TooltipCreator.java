package info.u_team.u_team_core.util;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TooltipCreator {
	
	private static final Object[] EMPTY = new Object[0];
	
	public static TranslatableComponent create(Item item, String category, int line) {
		return create(item, category, line, EMPTY);
	}
	
	public static TranslatableComponent create(Item item, String category, int line, Object... args) {
		if (!category.isEmpty()) {
			category += ".";
		}
		return new TranslatableComponent(item.getDescriptionId() + ".tooltip." + category + line, args);
	}
	
	public static TranslatableComponent create(Block block, String category, int line) {
		return create(block, category, line, EMPTY);
	}
	
	public static TranslatableComponent create(Block block, String category, int line, Object... args) {
		if (!category.isEmpty()) {
			category += ".";
		}
		return new TranslatableComponent(block.getDescriptionId() + ".tooltip." + category + line, args);
	}
	
	public static TranslatableComponent create(String modid, String key, String category, int line) {
		return create(modid, key, category, line, EMPTY);
	}
	
	public static TranslatableComponent create(String modid, String key, String category, int line, Object... args) {
		if (!category.isEmpty()) {
			category += ".";
		}
		return new TranslatableComponent("general." + modid + "." + key + ".tooltip." + category + line, args);
	}
	
}
