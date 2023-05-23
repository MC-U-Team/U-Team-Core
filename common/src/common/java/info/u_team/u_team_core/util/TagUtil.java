package info.u_team.u_team_core.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class TagUtil {
	
	public static TagKey<Block> createBlockTag(String modid, String name) {
		return createBlockTag(new ResourceLocation(modid, name));
	}
	
	public static TagKey<Block> createBlockTag(ResourceLocation location) {
		return createTag(Registries.BLOCK, location);
	}
	
	public static TagKey<Item> createItemTag(String modid, String name) {
		return createItemTag(new ResourceLocation(modid, name));
	}
	
	public static TagKey<Item> createItemTag(ResourceLocation location) {
		return createTag(Registries.ITEM, location);
	}
	
	public static TagKey<Fluid> createFluidTag(String modid, String name) {
		return createFluidTag(new ResourceLocation(modid, name));
	}
	
	public static TagKey<Fluid> createFluidTag(ResourceLocation location) {
		return createTag(Registries.FLUID, location);
	}
	
	public static TagKey<EntityType<?>> createEntityTypeTag(String modid, String name) {
		return createEntityTypeTag(new ResourceLocation(modid, name));
	}
	
	public static TagKey<EntityType<?>> createEntityTypeTag(ResourceLocation location) {
		return createTag(Registries.ENTITY_TYPE, location);
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
}
