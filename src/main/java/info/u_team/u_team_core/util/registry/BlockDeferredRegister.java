package info.u_team.u_team_core.util.registry;

import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

import info.u_team.u_team_core.api.registry.IUBlockRegistryType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

public class BlockDeferredRegister {
	
	public final DeferredRegister<Block> blocks;
	public final DeferredRegister<Item> items;
	
	public BlockDeferredRegister(String modid) {
		blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
		items = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
	}
	
	public <I extends Block> RegistryObject<I> registerCommon(String name, Supplier<? extends I> supplier) {
		return blocks.register(name, supplier);
	}
	
	public <I extends Block & IUBlockRegistryType> Pair<RegistryObject<I>, RegistryObject<? extends Item>> register(String name, Supplier<? extends I> supplier) {
		return Pair.of(blocks.register(name, supplier), items.register(name, () -> supplier.get().getBlockItem()));
	}
	
	public void register(IEventBus bus) {
		blocks.register(bus);
		items.register(bus);
	}
	
}
