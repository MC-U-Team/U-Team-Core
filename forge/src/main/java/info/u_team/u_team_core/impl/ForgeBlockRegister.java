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
import info.u_team.u_team_core.impl.ForgeCommonRegister.ForgeRegistryEntry;
import info.u_team.u_team_core.impl.common.CommonBlockRegistryEntry;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

public class ForgeBlockRegister implements BlockRegister {
	
	private final ForgeCommonRegister<Block> blocks;
	private final ForgeCommonRegister<Item> items;
	
	private final Map<ForgeRegistryEntry<? extends Block>, ForgeRegistryEntry<? extends Item>> blockToItemsMap;
	
	ForgeBlockRegister(String modid) {
		blocks = new ForgeCommonRegister<>(Registries.BLOCK, modid);
		items = new ForgeCommonRegister<>(Registries.ITEM, modid);
		blockToItemsMap = new LinkedHashMap<>();
	}
	
	@Override
	public <B extends Block & BlockItemProvider, I extends Item> ForgeBlockRegistryEntry<B, I> register(String name, Supplier<? extends B> supplier) {
		final ForgeRegistryEntry<B> block = blocks.register(name, supplier);
		final ForgeRegistryEntry<I> item = new ForgeRegistryEntry<>(RegistryObject.create(new ResourceLocation(blocks.getModid(), name), ForgeRegistries.ITEMS));
		
		blockToItemsMap.put(block, item);
		
		return new ForgeBlockRegistryEntry<>(block, item);
	}
	
	@Override
	public <B extends Block, I extends Item> ForgeBlockRegistryEntry<B, I> register(String name, Supplier<? extends B> blockSupplier, Function<Block, ? extends I> itemFunction) {
		return register(name, blockSupplier, () -> itemFunction.apply(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blocks.getModid(), name))));
	}
	
	@Override
	public <B extends Block, I extends Item> ForgeBlockRegistryEntry<B, I> register(String name, Supplier<? extends B> blockSupplier, Supplier<? extends I> itemSupplier) {
		final ForgeRegistryEntry<B> block = blocks.register(name, blockSupplier);
		final ForgeRegistryEntry<I> item = items.register(name, itemSupplier);
		return new ForgeBlockRegistryEntry<>(block, item);
	}
	
	@Override
	public <B extends Block> ForgeRegistryEntry<B> registerBlock(String name, Supplier<? extends B> supplier) {
		return blocks.register(name, supplier);
	}
	
	@Override
	public void register() {
		blocks.register();
		items.register();
		BusRegister.registerMod(bus -> bus.addListener(this::registerItems));
	}
	
	private void registerItems(RegisterEvent event) {
		if (event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS)) {
			blockToItemsMap.forEach((blockObject, itemObject) -> {
				final Block block = blockObject.get();
				if (block instanceof final BlockItemProvider blockItemProvider) {
					final Item blockItem = blockItemProvider.blockItem();
					if (blockItem != null) {
						event.register(ForgeRegistries.Keys.ITEMS, itemObject.getId(), () -> blockItem);
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
	public ForgeCommonRegister<Block> getBlockRegister() {
		return blocks;
	}
	
	@Override
	public ForgeCommonRegister<Item> getItemRegister() {
		return items;
	}
	
	public static class ForgeBlockRegistryEntry<B extends Block, I extends Item> extends CommonBlockRegistryEntry<B, I, ForgeRegistryEntry<B>, ForgeRegistryEntry<I>> {
		
		ForgeBlockRegistryEntry(ForgeRegistryEntry<B> block, ForgeRegistryEntry<I> item) {
			super(block, item);
		}
		
		public RegistryObject<B> getBlockRegistryObject() {
			return getBlockEntry().getRegistryObject();
		}
		
		public RegistryObject<I> getItemRegistryObject() {
			return getItemEntry().getRegistryObject();
		}
	}
	
	public static class Factory implements BlockRegister.Factory {
		
		@Override
		public BlockRegister create(String modid) {
			return new ForgeBlockRegister(modid);
		}
	}
}
