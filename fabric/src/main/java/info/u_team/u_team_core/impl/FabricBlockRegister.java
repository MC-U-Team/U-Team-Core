package info.u_team.u_team_core.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.block.BlockItemProvider;
import info.u_team.u_team_core.api.registry.BlockRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.impl.FabricCommonRegister.FabricRegistryEntry;
import info.u_team.u_team_core.impl.common.CommonBlockRegistryEntry;
import info.u_team.u_team_core.util.CastUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public class FabricBlockRegister implements BlockRegister {
	
	private final FabricCommonRegister<Block> blocks;
	private final FabricCommonRegister<Item> items;
	
	private final Map<FabricRegistryEntry<? extends Block>, FabricRegistryEntry<? extends Item>> blockToItemsMap;
	
	FabricBlockRegister(String modid) {
		blocks = new FabricCommonRegister<>(Registries.BLOCK, modid);
		items = new FabricCommonRegister<>(Registries.ITEM, modid);
		blockToItemsMap = new LinkedHashMap<>();
	}
	
	@Override
	public <B extends Block & BlockItemProvider, I extends Item> FabricBlockRegistryEntry<B, I> register(String name, Supplier<? extends B> supplier) {
		final FabricRegistryEntry<B> block = blocks.register(name, supplier);
		
		final ResourceLocation id = new ResourceLocation(items.getModid(), name);
		final ResourceKey<I> key = CastUtil.uncheckedCast(ResourceKey.create(items.getRegistryKey(), id));
		final FabricRegistryEntry<I> item = new FabricRegistryEntry<>(id, key);
		
		blockToItemsMap.put(block, item);
		
		return new FabricBlockRegistryEntry<>(block, item);
	}
	
	@Override
	public <B extends Block, I extends Item> FabricBlockRegistryEntry<B, I> register(String name, Supplier<? extends B> blockSupplier, Function<Block, ? extends I> itemFunction) {
		return register(name, blockSupplier, () -> itemFunction.apply(BuiltInRegistries.BLOCK.get(new ResourceLocation(blocks.getModid(), name))));
	}
	
	@Override
	public <B extends Block, I extends Item> FabricBlockRegistryEntry<B, I> register(String name, Supplier<? extends B> blockSupplier, Supplier<? extends I> itemSupplier) {
		final FabricRegistryEntry<B> block = blocks.register(name, blockSupplier);
		final FabricRegistryEntry<I> item = items.register(name, itemSupplier);
		return new FabricBlockRegistryEntry<>(block, item);
	}
	
	@Override
	public <B extends Block> FabricRegistryEntry<B> registerBlock(String name, Supplier<? extends B> supplier) {
		return blocks.register(name, supplier);
	}
	
	@Override
	public void register() {
		blocks.register();
		items.register();
		blockToItemsMap.forEach((blockEntry, itemEntry) -> {
			final Block block = blockEntry.get();
			if (block instanceof final BlockItemProvider blockItemProvider) {
				final Item blockItem = blockItemProvider.blockItem();
				if (blockItem != null) {
					Registry.register(BuiltInRegistries.ITEM, itemEntry.getId(), blockItem);
					itemEntry.updateReference(CastUtil.uncheckedCast(BuiltInRegistries.ITEM));
				}
			}
		});
	}
	
	@Override
	public String getModid() {
		return blocks.getModid();
	}
	
	@Override
	public Iterator<RegistryEntry<Block>> iterator() {
		return blocks.iterator();
	}
	
	@Override
	public Iterable<Block> blockIterable() {
		return blocks.entryIterable();
	}
	
	@Override
	public Iterable<Item> itemIterable() {
		return () -> blocks.getEntries().stream().map(block -> {
			final Item item = block.get().asItem();
			if (item != null && item != Items.AIR) {
				return Optional.of(item);
			}
			return Optional.<Item> empty();
		}).flatMap(Optional::stream).iterator();
	}
	
	@Override
	public FabricCommonRegister<Block> getBlockRegister() {
		return blocks;
	}
	
	@Override
	public FabricCommonRegister<Item> getItemRegister() {
		return items;
	}
	
	public static class FabricBlockRegistryEntry<B extends Block, I extends Item> extends CommonBlockRegistryEntry<B, I, FabricRegistryEntry<B>, FabricRegistryEntry<I>> {
		
		FabricBlockRegistryEntry(FabricRegistryEntry<B> block, FabricRegistryEntry<I> item) {
			super(block, item);
		}
	}
	
	public static class Factory implements BlockRegister.Factory {
		
		@Override
		public BlockRegister create(String modid) {
			return new FabricBlockRegister(modid);
		}
	}
}
