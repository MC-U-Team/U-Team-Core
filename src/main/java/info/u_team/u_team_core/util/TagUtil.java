package info.u_team.u_team_core.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Ingredient.TagValue;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class TagUtil {
	
	public static TagKey<Block> createBlockTag(String modid, String name) {
		return createBlockTag(new ResourceLocation(modid, name));
	}
	
	public static TagKey<Block> createBlockTag(ResourceLocation location) {
		return createTag(ForgeRegistries.Keys.BLOCKS, location);
	}
	
	public static TagKey<Item> createItemTag(String modid, String name) {
		return createItemTag(new ResourceLocation(modid, name));
	}
	
	public static TagKey<Item> createItemTag(ResourceLocation location) {
		return createTag(ForgeRegistries.Keys.ITEMS, location);
	}
	
	public static TagKey<Fluid> createFluidTag(String modid, String name) {
		return createFluidTag(new ResourceLocation(modid, name));
	}
	
	public static TagKey<Fluid> createFluidTag(ResourceLocation location) {
		return createTag(ForgeRegistries.Keys.FLUIDS, location);
	}
	
	public static TagKey<EntityType<?>> createEntityTypeTag(String modid, String name) {
		return createEntityTypeTag(new ResourceLocation(modid, name));
	}
	
	public static TagKey<EntityType<?>> createEntityTypeTag(ResourceLocation location) {
		return createTag(ForgeRegistries.Keys.ENTITY_TYPES, location);
	}
	
	public static <T> TagKey<T> createTag(ResourceKey<? extends Registry<T>> key, ResourceLocation location) {
		return TagKey.create(key, location);
	}
	
	public static TagKey<Block> fromItemTag(TagKey<Item> block) {
		return createBlockTag(block.location());
	}
	
	public static TagKey<Item> fromBlockTag(TagKey<Block> block) {
		return createItemTag(block.location());
	}
	
	public static Ingredient getSerializableIngredientOfTag(TagKey<Item> tag) {
		return Ingredient.fromValues(Stream.of(new TagValue(tag) {
			
			@Override
			public Collection<ItemStack> getItems() {
				return Arrays.asList(new ItemStack(Items.ACACIA_BOAT)); // Return default value, so the ingredient will serialize our tag.
			}
		}));
	}
}
