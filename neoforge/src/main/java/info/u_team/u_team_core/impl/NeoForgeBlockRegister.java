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
import info.u_team.u_team_core.impl.NeoForgeCommonRegister.NeoForgeRegistryEntry;
import info.u_team.u_team_core.impl.common.CommonBlockRegistryEntry;
import info.u_team.u_team_core.util.RegistryUtil;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.RegisterEvent;

public class NeoForgeBlockRegister implements BlockRegister {
	
	private final NeoForgeCommonRegister<Block> blocks;
	private final NeoForgeCommonRegister<Item> items;
	
	private final Map<NeoForgeRegistryEntry<Block, ? extends Block>, NeoForgeRegistryEntry<Item, ? extends Item>> blockToItemsMap;
	
	NeoForgeBlockRegister(String modid) {
		blocks = new NeoForgeCommonRegister<>(Registries.BLOCK, modid);
		items = new NeoForgeCommonRegister<>(Registries.ITEM, modid);
		blockToItemsMap = new LinkedHashMap<>();
	}
	
	@Override
	public <B extends Block & BlockItemProvider, I extends Item> NeoForgeBlockRegistryEntry<B, I> register(String name, Supplier<? extends B> supplier) {
		final NeoForgeRegistryEntry<Block, B> block = blocks.register(name, supplier);
		final NeoForgeRegistryEntry<Item, I> item = new NeoForgeRegistryEntry<>(DeferredHolder.create(Registries.ITEM, new ResourceLocation(blocks.getModid(), name)));
		
		blockToItemsMap.put(block, item);
		
		return new NeoForgeBlockRegistryEntry<>(block, item);
	}
	
	@Override
	public <B extends Block, I extends Item> NeoForgeBlockRegistryEntry<B, I> register(String name, Supplier<? extends B> blockSupplier, Function<Block, ? extends I> itemFunction) {
		return register(name, blockSupplier, () -> itemFunction.apply(RegistryUtil.getBuiltInRegistry(Registries.BLOCK).get(new ResourceLocation(blocks.getModid(), name))));
	}
	
	@Override
	public <B extends Block, I extends Item> NeoForgeBlockRegistryEntry<B, I> register(String name, Supplier<? extends B> blockSupplier, Supplier<? extends I> itemSupplier) {
		final NeoForgeRegistryEntry<Block, B> block = blocks.register(name, blockSupplier);
		final NeoForgeRegistryEntry<Item, I> item = items.register(name, itemSupplier);
		return new NeoForgeBlockRegistryEntry<>(block, item);
	}
	
	@Override
	public <B extends Block> NeoForgeRegistryEntry<Block, B> registerBlock(String name, Supplier<? extends B> supplier) {
		return blocks.register(name, supplier);
	}
	
	@Override
	public void register() {
		blocks.register();
		items.register();
		BusRegister.registerMod(bus -> bus.addListener(this::registerItems));
	}
	
	private void registerItems(RegisterEvent event) {
		if (event.getRegistryKey().equals(Registries.ITEM)) {
			blockToItemsMap.forEach((blockObject, itemObject) -> {
				final Block block = blockObject.get();
				if (block instanceof final BlockItemProvider blockItemProvider) {
					final Item blockItem = blockItemProvider.blockItem();
					if (blockItem != null) {
						event.register(Registries.ITEM, itemObject.getId(), () -> blockItem);
					}
				}
			});
		}
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
	public NeoForgeCommonRegister<Block> getBlockRegister() {
		return blocks;
	}
	
	@Override
	public NeoForgeCommonRegister<Item> getItemRegister() {
		return items;
	}
	
	public static class NeoForgeBlockRegistryEntry<B extends Block, I extends Item> extends CommonBlockRegistryEntry<B, I, NeoForgeRegistryEntry<Block, B>, NeoForgeRegistryEntry<Item, I>> {
		
		NeoForgeBlockRegistryEntry(NeoForgeRegistryEntry<Block, B> block, NeoForgeRegistryEntry<Item, I> item) {
			super(block, item);
		}
		
		public DeferredHolder<Block, B> getBlockRegistryHolder() {
			return getBlockEntry().getRegistryHolder();
		}
		
		public DeferredHolder<Item, I> getItemRegistryHolder() {
			return getItemEntry().getRegistryHolder();
		}
	}
	
	public static class Factory implements BlockRegister.Factory {
		
		@Override
		public BlockRegister create(String modid) {
			return new NeoForgeBlockRegister(modid);
		}
	}
}
