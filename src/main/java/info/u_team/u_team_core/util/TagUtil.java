package info.u_team.u_team_core.util;

import java.util.*;
import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.Ingredient.TagList;
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
	
	public static Ingredient getSerializableIngredientOfTag(Tag<Item> tag) {
		return Ingredient.fromItemListStream(Stream.of(new TagList(tag) {
			
			@Override
			public Collection<ItemStack> getStacks() {
				return Arrays.asList(new ItemStack(Items.ACACIA_BOAT)); // Return default value, so the ingredient will serialize our tag.
			}
		}));
	}
}
