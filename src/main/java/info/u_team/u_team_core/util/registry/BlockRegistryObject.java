package info.u_team.u_team_core.util.registry;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class BlockRegistryObject<B extends Block, I extends Item> implements Supplier<B> {
	
	private final RegistryObject<B> block;
	private final RegistryObject<I> item;
	
	public BlockRegistryObject(RegistryObject<B> block, RegistryObject<I> item) {
		this.block = block;
		this.item = item;
	}
	
	@Override
	public B get() {
		return block.get();
	}
	
	public boolean hasItem() {
		return item.isPresent();
	}
	
	public I getItem() {
		return item.get();
	}
	
}
