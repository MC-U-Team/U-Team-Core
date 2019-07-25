package info.u_team.u_team_core.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.*;
import net.minecraft.util.ResourceLocation;

public class TagUtil {
	
	public static Tag<Block> createBlockTag(String modid, String name) {
		return new BlockTags.Wrapper(new ResourceLocation(modid, name));
	}
	
	public static Tag<Item> createItemTag(String modid, String name) {
		return new ItemTags.Wrapper(new ResourceLocation(modid, name));
	}
	
	public static Tag<Block> fromItemTag(Tag<Item> block) {
		return new BlockTags.Wrapper(block.getId());
	}
	
	public static Tag<Item> fromBlockTag(Tag<Block> block) {
		return new ItemTags.Wrapper(block.getId());
	}
}
