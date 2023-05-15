package info.u_team.u_team_core.util.registry;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.block.BlockItemProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

public class BlockDeferredRegister implements Iterable<RegistryObject<Block>> {
	
	public static BlockDeferredRegister create(String modid) {
		return new BlockDeferredRegister(modid);
	}
	
	private final CommonDeferredRegister<Block> blocks;
	private final CommonDeferredRegister<Item> items;
	
	private final Map<RegistryObject<? extends Block>, RegistryObject<? extends Item>> blockToItemsMap;
	
	protected BlockDeferredRegister(String modid) {
		blocks = CommonDeferredRegister.create(ForgeRegistries.BLOCKS, modid);
		items = CommonDeferredRegister.create(ForgeRegistries.ITEMS, modid);
		blockToItemsMap = new LinkedHashMap<>();
	}
	
	public <B extends Block & BlockItemProvider, I extends Item> BlockRegistryObject<B, I> register(String name, Supplier<? extends B> supplier) {
		final RegistryObject<B> block = blocks.register(name, supplier);
		final RegistryObject<I> item = RegistryObject.create(new ResourceLocation(blocks.getModid(), name), ForgeRegistries.ITEMS);
		
		blockToItemsMap.put(block, item);
		
		return new BlockRegistryObject<>(block, item);
	}
	
	public <B extends Block, I extends Item> BlockRegistryObject<B, I> register(String name, Supplier<? extends B> blockSupplier, Function<Block, ? extends I> itemFunction) {
		return register(name, blockSupplier, () -> itemFunction.apply(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blocks.getModid(), name))));
	}
	
	public <B extends Block, I extends Item> BlockRegistryObject<B, I> register(String name, Supplier<? extends B> blockSupplier, Supplier<? extends I> itemSupplier) {
		final RegistryObject<B> block = blocks.register(name, blockSupplier);
		final RegistryObject<I> item = items.register(name, itemSupplier);
		return new BlockRegistryObject<>(block, item);
	}
	
	public <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier) {
		return blocks.register(name, supplier);
	}
	
	public void register(IEventBus bus) {
		blocks.register(bus);
		items.register(bus);
		bus.addListener(this::registerItems);
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
	public Iterator<RegistryObject<Block>> iterator() {
		return blocks.iterator();
	}
	
	public Iterable<Block> blockIterable() {
		return blocks.entryIterable();
	}
	
	public Iterable<Item> itemIterable() {
		return () -> blocks.getEntries().stream().map(block -> {
			final Item item = block.get().asItem();
			if (item != null && item != Items.AIR) {
				return Optional.of(item);
			}
			return Optional.<Item> empty();
		}).flatMap(Optional::stream).iterator();
	}
	
	public CommonDeferredRegister<Block> getBlockRegister() {
		return blocks;
	}
	
	public CommonDeferredRegister<Item> getItemRegister() {
		return items;
	}
	
}
