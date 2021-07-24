package info.u_team.u_team_core.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Ingredient.TagValue;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class TagUtil {
	
	public static Named<Block> createBlockTag(String modid, String name) {
		return createBlockTag(new ResourceLocation(modid, name));
	}
	
	public static Named<Block> createBlockTag(ResourceLocation location) {
		final Optional<? extends Named<Block>> optional = BlockTags.getWrappers().stream().filter(tag -> tag.getName().equals(location)).findAny();
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return BlockTags.bind(location.toString());
		}
	}
	
	public static Named<Item> createItemTag(String modid, String name) {
		return createItemTag(new ResourceLocation(modid, name));
	}
	
	public static Named<Item> createItemTag(ResourceLocation location) {
		final Optional<? extends Named<Item>> optional = ItemTags.getWrappers().stream().filter(tag -> tag.getName().equals(location)).findAny();
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return ItemTags.bind(location.toString());
		}
	}
	
	public static Named<Fluid> createFluidTag(String modid, String name) {
		return createFluidTag(new ResourceLocation(modid, name));
	}
	
	public static Named<Fluid> createFluidTag(ResourceLocation location) {
		final Optional<? extends Named<Fluid>> optional = FluidTags.getWrappers().stream().filter(tag -> tag.getName().equals(location)).findAny();
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return FluidTags.bind(location.toString());
		}
	}
	
	public static Named<EntityType<?>> createEntityTypeTag(String modid, String name) {
		return createEntityTypeTag(new ResourceLocation(modid, name));
	}
	
	public static Named<EntityType<?>> createEntityTypeTag(ResourceLocation location) {
		final Optional<? extends Named<EntityType<?>>> optional = EntityTypeTags.getWrappers().stream().filter(tag -> tag.getName().equals(location)).findAny();
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return EntityTypeTags.bind(location.toString());
		}
	}
	
	public static Named<Block> fromItemTag(Named<Item> block) {
		return createBlockTag(block.getName());
	}
	
	public static Named<Item> fromBlockTag(Named<Block> block) {
		return createItemTag(block.getName());
	}
	
	public static Ingredient getSerializableIngredientOfTag(Tag<Item> tag) {
		return Ingredient.fromValues(Stream.of(new TagValue(tag) {
			
			@Override
			public Collection<ItemStack> getItems() {
				return Arrays.asList(new ItemStack(Items.ACACIA_BOAT)); // Return default value, so the ingredient will serialize our tag.
			}
		}));
	}
}
