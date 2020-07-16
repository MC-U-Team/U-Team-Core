package info.u_team.u_team_core.util;

import java.util.*;
import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.Ingredient.TagList;
import net.minecraft.tags.*;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.util.ResourceLocation;

public class TagUtil {
	
	public static INamedTag<Block> createBlockTag(String modid, String name) {
		return createBlockTag(new ResourceLocation(modid, name));
	}
	
	public static INamedTag<Block> createBlockTag(ResourceLocation location) {
		return BlockTags.makeWrapperTag(location.toString());
	}
	
	public static INamedTag<Item> createItemTag(String modid, String name) {
		return createItemTag(new ResourceLocation(modid, name));
	}
	
	public static INamedTag<Item> createItemTag(ResourceLocation location) {
		return ItemTags.makeWrapperTag(location.toString());
	}
	
	public static INamedTag<Fluid> createFluidTag(String modid, String name) {
		return createFluidTag(new ResourceLocation(modid, name));
	}
	
	public static INamedTag<Fluid> createFluidTag(ResourceLocation location) {
		return FluidTags.makeWrapperTag(location.toString());
	}
	
	public static INamedTag<EntityType<?>> createEntityTypeTag(String modid, String name) {
		return createEntityTypeTag(new ResourceLocation(modid, name));
	}
	
	public static INamedTag<EntityType<?>> createEntityTypeTag(ResourceLocation location) {
		return EntityTypeTags.func_232896_a_(location.toString());
	}
	
	public static INamedTag<Block> fromItemTag(INamedTag<Item> block) {
		return BlockTags.makeWrapperTag(block.getName().toString());
	}
	
	public static INamedTag<Item> fromBlockTag(INamedTag<Block> block) {
		return ItemTags.makeWrapperTag(block.getName().toString());
	}
	
	public static Ingredient getSerializableIngredientOfTag(ITag<Item> tag) {
		return Ingredient.fromItemListStream(Stream.of(new TagList(tag) {
			
			@Override
			public Collection<ItemStack> getStacks() {
				return Arrays.asList(new ItemStack(Items.ACACIA_BOAT)); // Return default value, so the ingredient will serialize our tag.
			}
		}));
	}
}
