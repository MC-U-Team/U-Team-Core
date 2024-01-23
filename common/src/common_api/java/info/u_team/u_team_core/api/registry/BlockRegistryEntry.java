package info.u_team.u_team_core.api.registry;

import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface BlockRegistryEntry<B extends Block, I extends Item> extends RegistryEntry<B> {
	
	I getItem();
	
	ResourceLocation getItemId();
	
	ResourceKey<I> getItemKey();
	
	Optional<Holder<I>> getItemHolder();
	
	boolean isItemPresent();
	
	RegistryEntry<I> getItemRegistryEntry();
	
}
