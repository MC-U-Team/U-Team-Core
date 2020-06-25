package info.u_team.u_team_core.util.registry;

import java.util.*;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.*;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

public class BlockDeferredRegister {
	
	public static BlockDeferredRegister create(String modid) {
		return new BlockDeferredRegister(modid);
	}
	
	private final String modid;
	
	private final DeferredRegister<Block> blocks;
	
	private final Map<RegistryObject<? extends Block>, RegistryObject<? extends Item>> blockToItemsMap;
	
	protected BlockDeferredRegister(String modid) {
		this.modid = modid;
		blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
		blockToItemsMap = new HashMap<>();
	}
	
	public <B extends Block & IUBlockRegistryType, I extends BlockItem> BlockRegistryObject<B, I> register(String name, Supplier<? extends B> supplier) {
		final RegistryObject<B> block = blocks.register(name, supplier);
		final RegistryObject<I> item = RegistryObject.of(new ResourceLocation(modid, name), ForgeRegistries.ITEMS);
		
		blockToItemsMap.put(block, item);
		
		return new BlockRegistryObject<B, I>(block, item);
	}
	
	public <B extends Block> RegistryObject<B> registerBlock(final String name, final Supplier<? extends B> supplier) {
		return blocks.register(name, supplier);
	}
	
	public void register(IEventBus bus) {
		blocks.register(bus);
		bus.addGenericListener(Item.class, this::registerItems);
	}
	
	@SuppressWarnings("unchecked")
	private void registerItems(Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		
		blockToItemsMap.forEach((blockObject, itemObject) -> {
			final Block block = blockObject.get();
			if (block instanceof IBlockItemProvider) {
				final BlockItem blockItem = ((IBlockItemProvider) block).getBlockItem();
				registry.register(blockItem.setRegistryName(itemObject.getId()));
				((RegistryObject<Item>) itemObject).updateReference(registry);
			}
		});
	}
	
}
