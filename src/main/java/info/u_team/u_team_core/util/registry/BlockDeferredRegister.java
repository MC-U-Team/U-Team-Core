package info.u_team.u_team_core.util.registry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.BlockItemProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockDeferredRegister {
	
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
	
	public <B extends Block & BlockItemProvider, I extends BlockItem> BlockRegistryObject<B, I> register(String name, Supplier<? extends B> supplier) {
		final RegistryObject<B> block = blocks.register(name, supplier);
		final RegistryObject<I> item = RegistryObject.of(new ResourceLocation(blocks.getModid(), name), ForgeRegistries.ITEMS);
		
		blockToItemsMap.put(block, item);
		
		return new BlockRegistryObject<>(block, item);
	}
	
	public <B extends Block, I extends BlockItem> BlockRegistryObject<B, I> register(String name, Supplier<? extends B> blockSupplier, Supplier<? extends I> itemSupplier) {
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
		bus.addGenericListener(Item.class, this::registerItems);
	}
	
	@SuppressWarnings("unchecked")
	private void registerItems(Register<Item> event) {
		final var registry = event.getRegistry();
		
		blockToItemsMap.forEach((blockObject, itemObject) -> {
			final var block = blockObject.get();
			if (block instanceof BlockItemProvider) {
				final var blockItem = ((BlockItemProvider) block).getBlockItem();
				if (blockItem != null) {
					registry.register(blockItem.setRegistryName(itemObject.getId()));
					((RegistryObject<Item>) itemObject).updateReference(registry);
				}
			}
		});
	}
	
	public CommonDeferredRegister<Block> getBlockRegister() {
		return blocks;
	}
	
	public CommonDeferredRegister<Item> getItemRegister() {
		return items;
	}
	
}
