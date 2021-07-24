package info.u_team.u_team_core.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.text.TranslationTextComponent;

public class TooltipCreator {
	
	private static final Object[] EMPTY = new Object[0];
	
	public static TranslationTextComponent create(Item item, String category, int line) {
		return create(item, category, line, EMPTY);
	}
	
	public static TranslationTextComponent create(Item item, String category, int line, Object... args) {
		if (!category.isEmpty()) {
			category += ".";
		}
		return new TranslationTextComponent(item.getDescriptionId() + ".tooltip." + category + line, args);
	}
	
	public static TranslationTextComponent create(Block block, String category, int line) {
		return create(block, category, line, EMPTY);
	}
	
	public static TranslationTextComponent create(Block block, String category, int line, Object... args) {
		if (!category.isEmpty()) {
			category += ".";
		}
		return new TranslationTextComponent(block.getDescriptionId() + ".tooltip." + category + line, args);
	}
	
	public static TranslationTextComponent create(String modid, String key, String category, int line) {
		return create(modid, key, category, line, EMPTY);
	}
	
	public static TranslationTextComponent create(String modid, String key, String category, int line, Object... args) {
		if (!category.isEmpty()) {
			category += ".";
		}
		return new TranslationTextComponent("general." + modid + "." + key + ".tooltip." + category + line, args);
	}
	
}
