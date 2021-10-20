package info.u_team.u_team_core.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.StaticTagHelper;
import net.minecraft.tags.StaticTags;
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
import net.minecraftforge.common.Tags.IOptionalNamedTag;
import net.minecraftforge.registries.ForgeRegistries;

public class TagUtil {
	
	public static Named<Block> createBlockTag(String modid, String name) {
		return createBlockTag(new ResourceLocation(modid, name));
	}
	
	public static Named<Block> createBlockTag(ResourceLocation location) {
		return createTag(ForgeRegistries.Keys.BLOCKS, location);
	}
	
	public static Named<Item> createItemTag(String modid, String name) {
		return createItemTag(new ResourceLocation(modid, name));
	}
	
	public static Named<Item> createItemTag(ResourceLocation location) {
		return createTag(ForgeRegistries.Keys.ITEMS, location);
	}
	
	public static Named<Fluid> createFluidTag(String modid, String name) {
		return createFluidTag(new ResourceLocation(modid, name));
	}
	
	public static Named<Fluid> createFluidTag(ResourceLocation location) {
		return createTag(ForgeRegistries.Keys.FLUIDS, location);
	}
	
	public static Named<EntityType<?>> createEntityTypeTag(String modid, String name) {
		return createEntityTypeTag(new ResourceLocation(modid, name));
	}
	
	public static Named<EntityType<?>> createEntityTypeTag(ResourceLocation location) {
		return createTag(ForgeRegistries.Keys.ENTITY_TYPES, location);
	}
	
	public static <T> Named<T> createTag(ResourceKey<? extends Registry<T>> key, ResourceLocation location) {
		final StaticTagHelper<T> helper = CastUtil.uncheckedCast(StaticTags.get(key.location()));
		
		return helper.wrappers.stream().filter(tag -> tag.getName().equals(location)).map(tag -> (Named<T>) tag).findAny().orElse(helper.bind(location.toString()));
	}
	
	public static IOptionalNamedTag<Block> createOptionalBlockTag(String modid, String name) {
		return createOptionalBlockTag(new ResourceLocation(modid, name));
	}
	
	public static IOptionalNamedTag<Block> createOptionalBlockTag(ResourceLocation location) {
		return createOptionalTag(ForgeRegistries.Keys.BLOCKS, location);
	}
	
	public static IOptionalNamedTag<Item> createOptionalItemTag(String modid, String name) {
		return createOptionalItemTag(new ResourceLocation(modid, name));
	}
	
	public static IOptionalNamedTag<Item> createOptionalItemTag(ResourceLocation location) {
		return createOptionalTag(ForgeRegistries.Keys.ITEMS, location);
	}
	
	public static IOptionalNamedTag<Fluid> createOptionalFluidTag(String modid, String name) {
		return createOptionalFluidTag(new ResourceLocation(modid, name));
	}
	
	public static IOptionalNamedTag<Fluid> createOptionalFluidTag(ResourceLocation location) {
		return createOptionalTag(ForgeRegistries.Keys.FLUIDS, location);
	}
	
	public static IOptionalNamedTag<EntityType<?>> createOptionalEntityTypeTag(String modid, String name) {
		return createOptionalEntityTypeTag(new ResourceLocation(modid, name));
	}
	
	public static IOptionalNamedTag<EntityType<?>> createOptionalEntityTypeTag(ResourceLocation location) {
		return createOptionalTag(ForgeRegistries.Keys.ENTITY_TYPES, location);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> IOptionalNamedTag<T> createOptionalTag(ResourceKey<? extends Registry<T>> key, ResourceLocation location) {
		final StaticTagHelper<T> helper = CastUtil.uncheckedCast(StaticTags.get(key.location()));
		
		return helper.wrappers.stream().filter(tag -> tag instanceof IOptionalNamedTag && tag.getName().equals(location)).map(tag -> (IOptionalNamedTag<T>) tag).findAny().orElse(helper.createOptional(location, null));
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
