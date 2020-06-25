package info.u_team.u_team_core.util.registry;

import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.IUBlockRegistryType;
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
	
	private final DeferredRegister<Block> blocksWithItemBlockProvider;
	
	protected BlockDeferredRegister(String modid) {
		this.modid = modid;
		blocksWithItemBlockProvider = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
	}
	
	public <B extends Block & IUBlockRegistryType, I extends BlockItem> BlockRegistryObject<B, I> register(String name, Supplier<? extends B> supplier) {
		final RegistryObject<B> block = blocksWithItemBlockProvider.register(name, supplier);
		final RegistryObject<I> item = RegistryObject.of(new ResourceLocation(modid, name), ForgeRegistries.ITEMS);
		
		return new BlockRegistryObject<B, I>(block, item);
	}
	
	public void register(IEventBus bus) {
		blocksWithItemBlockProvider.register(bus);
		bus.addGenericListener(Item.class, this::addItems);
	}
	
	private void addItems(Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		blocksWithItemBlockProvider.getEntries() //
				.stream() //
				.map(RegistryObject::get) //
				.filter(IUBlockRegistryType.class::isInstance) //
				.forEach(block -> registry.register(((IUBlockRegistryType) block).getBlockItem().setRegistryName(block.getRegistryName())));
	}
	
}
