package info.u_team.u_team_core.util.registry;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistryObject<B extends Block, I extends Item> implements Supplier<B> {
	
	private final RegistryObject<B> block;
	private final RegistryObject<I> item;
	
	public BlockRegistryObject(RegistryObject<B> block, RegistryObject<I> item) {
		this.block = block;
		this.item = item;
	}
	
	public boolean isPresent() {
		return block.isPresent();
	}
	
	@Override
	public B get() {
		return block.get();
	}
	
	public ResourceLocation getId() {
		return block.getId();
	}
	
	public RegistryObject<B> getBlockRegistryObject() {
		return block;
	}
	
	public boolean hasItem() {
		return item.isPresent();
	}
	
	public I getItem() {
		return item.get();
	}
	
	public ResourceLocation getItemId() {
		return item.getId();
	}
	
	public RegistryObject<I> getItemRegistryObject() {
		return item;
	}
	
}
