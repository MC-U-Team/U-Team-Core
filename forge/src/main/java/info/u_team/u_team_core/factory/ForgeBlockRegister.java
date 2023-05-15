package info.u_team.u_team_core.factory;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.collect.Streams;

import info.u_team.u_team_core.api.block.BlockItemProvider;
import info.u_team.u_team_core.api.registry.BlockRegister;
import info.u_team.u_team_core.api.registry.BlockRegistryEntry;
import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.factory.ForgeCommonRegister.ForgeRegistryEntry;
import info.u_team.u_team_core.util.registry.BlockDeferredRegister;
import info.u_team.u_team_core.util.registry.BlockRegistryObject;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ForgeBlockRegister implements BlockRegister {
	
	private final BlockDeferredRegister register;
	
	ForgeBlockRegister(String modid) {
		register = BlockDeferredRegister.create(modid);
	}
	
	@Override
	public <B extends Block & BlockItemProvider, I extends Item> ForgeBlockRegistryEntry<B, I> register(String name, Supplier<? extends B> supplier) {
		return new ForgeBlockRegistryEntry<>(register.register(name, supplier));
	}
	
	@Override
	public <B extends Block, I extends Item> ForgeBlockRegistryEntry<B, I> register(String name, Supplier<? extends B> blockSupplier, Function<Block, ? extends I> itemFunction) {
		return new ForgeBlockRegistryEntry<>(register.register(name, blockSupplier, itemFunction));
	}
	
	@Override
	public <B extends Block, I extends Item> ForgeBlockRegistryEntry<B, I> register(String name, Supplier<? extends B> blockSupplier, Supplier<? extends I> itemSupplier) {
		return new ForgeBlockRegistryEntry<>(register.register(name, blockSupplier, itemSupplier));
	}
	
	@Override
	public <B extends Block> ForgeRegistryEntry<B> registerBlock(String name, Supplier<? extends B> supplier) {
		return new ForgeRegistryEntry<>(register.registerBlock(name, supplier));
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(register::register);
	}
	
	@Override
	public String getModid() {
		return register.getBlockRegister().getModid();
	}
	
	@Override
	public Iterator<RegistryEntry<Block>> iterator() {
		return Streams.stream(register.iterator()).map(object -> (RegistryEntry<Block>) new ForgeRegistryEntry<>(object)).iterator();
	}
	
	@Override
	public Iterable<Block> blockIterable() {
		return register.blockIterable();
	}
	
	@Override
	public Iterable<Item> itemIterable() {
		return register.itemIterable();
	}
	
	@Override
	public CommonRegister<Block> getBlockRegister() {
		return new ForgeCommonRegister<>(register.getBlockRegister());
	}
	
	@Override
	public CommonRegister<Item> getItemRegister() {
		return new ForgeCommonRegister<>(register.getItemRegister());
	}
	
	public static class ForgeBlockRegistryEntry<B extends Block, I extends Item> implements BlockRegistryEntry<B, I> {
		
		private final BlockRegistryObject<B, I> object;
		
		ForgeBlockRegistryEntry(BlockRegistryObject<B, I> object) {
			this.object = object;
		}
		
		@Override
		public B get() {
			return object.get();
		}
		
		@Override
		public ResourceLocation getId() {
			return object.getId();
		}
		
		@Override
		public ResourceKey<B> getKey() {
			return object.getBlockRegistryObject().getKey();
		}
		
		@Override
		public Optional<Holder<B>> getHolder() {
			return object.getBlockRegistryObject().getHolder();
		}
		
		@Override
		public boolean isPresent() {
			return object.isPresent();
		}
		
		@Override
		public I getItem() {
			return object.getItem();
		}
		
		@Override
		public ResourceLocation getItemId() {
			return object.getItemId();
		}
		
		@Override
		public ResourceKey<I> getItemKey() {
			return object.getItemRegistryObject().getKey();
		}
		
		@Override
		public Optional<Holder<I>> getItemHolder() {
			return object.getItemRegistryObject().getHolder();
		}
		
		@Override
		public boolean isItemPresent() {
			return object.hasItem();
		}
		
		@Override
		public RegistryEntry<I> getItemRegistryEntry() {
			return new ForgeRegistryEntry<>(object.getItemRegistryObject());
		}
	}
	
	public static class Factory implements BlockRegister.Factory {
		
		@Override
		public BlockRegister create(String modid) {
			return new ForgeBlockRegister(modid);
		}
	}
}
