package info.u_team.u_team_core.impl.common;

import info.u_team.u_team_core.api.registry.BlockRegistryEntry;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public abstract class CommonBlockRegistryEntry<B extends Block, I extends Item, RB extends RegistryEntry<B>, RI extends RegistryEntry<I>> implements BlockRegistryEntry<B, I> {
	
	private final RB block;
	private final RI item;
	
	protected CommonBlockRegistryEntry(RB block, RI item) {
		this.block = block;
		this.item = item;
	}
	
	@Override
	public B get() {
		return block.get();
	}
	
	@Override
	public ResourceLocation getId() {
		return block.getId();
	}
	
	@Override
	public ResourceKey<B> getKey() {
		return block.getKey();
	}
	
	@Override
	public Holder<B> getHolder() {
		return block.getHolder();
	}
	
	@Override
	public boolean isPresent() {
		return block.isPresent();
	}
	
	@Override
	public I getItem() {
		return item.get();
	}
	
	@Override
	public ResourceLocation getItemId() {
		return item.getId();
	}
	
	@Override
	public ResourceKey<I> getItemKey() {
		return item.getKey();
	}
	
	@Override
	public Holder<I> getItemHolder() {
		return item.getHolder();
	}
	
	@Override
	public boolean isItemPresent() {
		return item.isPresent();
	}
	
	@Override
	public RegistryEntry<I> getItemRegistryEntry() {
		return item;
	}
	
	public RB getBlockEntry() {
		return block;
	}
	
	public RI getItemEntry() {
		return item;
	}
	
}
