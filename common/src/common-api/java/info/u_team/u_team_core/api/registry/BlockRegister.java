package info.u_team.u_team_core.api.registry;

import java.util.function.Function;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.block.BlockItemProvider;
import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface BlockRegister extends Iterable<RegistryEntry<Block>> {
	
	static BlockRegister create(String modid) {
		return Factory.INSTANCE.create(modid);
	}
	
	<B extends Block & BlockItemProvider, I extends BlockItem> BlockRegistryEntry<B, I> register(String name, Supplier<? extends B> supplier);
	
	<B extends Block, I extends BlockItem> BlockRegistryEntry<B, I> register(String name, Supplier<? extends B> blockSupplier, Function<Block, ? extends I> itemFunction);
	
	<B extends Block, I extends BlockItem> BlockRegistryEntry<B, I> register(String name, Supplier<? extends B> blockSupplier, Supplier<? extends I> itemSupplier);
	
	<B extends Block> RegistryEntry<B> registerBlock(String name, Supplier<? extends B> supplier);
	
	void register();
	
	String getModid();
	
	Iterable<Block> blockIterable();
	
	Iterable<Item> itemIterable();
	
	CommonRegister<Block> getBlockRegister();
	
	CommonRegister<Item> getItemRegister();
	
	interface Factory {
		
		Factory INSTANCE = ServiceUtil.load(Factory.class);
		
		BlockRegister create(String modid);
	}
	
}
