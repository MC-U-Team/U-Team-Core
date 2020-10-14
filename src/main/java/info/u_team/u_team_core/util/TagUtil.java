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
		final Optional<? extends INamedTag<Block>> optional = BlockTags.getAllTags().stream().filter(tag -> tag.getName().equals(location)).findAny();
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return BlockTags.makeWrapperTag(location.toString());
		}
	}
	
	public static INamedTag<Item> createItemTag(String modid, String name) {
		return createItemTag(new ResourceLocation(modid, name));
	}
	
	public static INamedTag<Item> createItemTag(ResourceLocation location) {
		final Optional<? extends INamedTag<Item>> optional = ItemTags.getAllTags().stream().filter(tag -> tag.getName().equals(location)).findAny();
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return ItemTags.makeWrapperTag(location.toString());
		}
	}
	
	public static INamedTag<Fluid> createFluidTag(String modid, String name) {
		return createFluidTag(new ResourceLocation(modid, name));
	}
	
	public static INamedTag<Fluid> createFluidTag(ResourceLocation location) {
		final Optional<? extends INamedTag<Fluid>> optional = FluidTags.getAllTags().stream().filter(tag -> tag.getName().equals(location)).findAny();
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return FluidTags.makeWrapperTag(location.toString());
		}
	}
	
	public static INamedTag<EntityType<?>> createEntityTypeTag(String modid, String name) {
		return createEntityTypeTag(new ResourceLocation(modid, name));
	}
	
	public static INamedTag<EntityType<?>> createEntityTypeTag(ResourceLocation location) {
		final Optional<? extends INamedTag<EntityType<?>>> optional = EntityTypeTags.getAllTags().stream().filter(tag -> tag.getName().equals(location)).findAny();
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return EntityTypeTags.getTagById(location.toString());
		}
	}
	
	public static INamedTag<Block> fromItemTag(INamedTag<Item> block) {
		return createBlockTag(block.getName());
	}
	
	public static INamedTag<Item> fromBlockTag(INamedTag<Block> block) {
		return createItemTag(block.getName());
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