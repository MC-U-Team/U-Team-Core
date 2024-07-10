package info.u_team.u_team_core.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeCommonRegister<C> implements CommonRegister<C> {
	
	private final DeferredRegister<C> register;
	private final String modid;
	
	NeoForgeCommonRegister(ResourceKey<? extends Registry<C>> key, String modid) {
		register = DeferredRegister.create(key, modid);
		this.modid = modid;
	}
	
	@Override
	public <E extends C> NeoForgeRegistryEntry<C, E> register(String name, Function<ResourceLocation, ? extends E> function) {
		return register(name, () -> function.apply(ResourceLocation.fromNamespaceAndPath(modid, name)));
	}
	
	@Override
	public <E extends C> NeoForgeRegistryEntry<C, E> register(String name, Supplier<? extends E> supplier) {
		return new NeoForgeRegistryEntry<>(register.register(name, supplier));
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(register::register);
	}
	
	@Override
	public String getModid() {
		return modid;
	}
	
	@Override
	public ResourceKey<? extends Registry<C>> getRegistryKey() {
		return register.getRegistryKey();
	}
	
	@Override
	public Collection<RegistryEntry<C>> getEntries() {
		return Collections.unmodifiableCollection(CastUtil.uncheckedCast(register.getEntries().stream().map(holder -> new NeoForgeRegistryEntry<>(holder)).collect(Collectors.toCollection(LinkedHashSet::new))));
	}
	
	public DeferredRegister<C> getDeferredRegister() {
		return register;
	}
	
	public static class NeoForgeRegistryEntry<C, E extends C> implements RegistryEntry<E> {
		
		private final DeferredHolder<C, E> holder;
		
		NeoForgeRegistryEntry(DeferredHolder<C, E> holder) {
			this.holder = holder;
		}
		
		@Override
		public E get() {
			return holder.get();
		}
		
		@Override
		public ResourceLocation getId() {
			return holder.getId();
		}
		
		@Override
		public ResourceKey<E> getKey() {
			return CastUtil.uncheckedCast(holder.getKey());
		}
		
		@Override
		public Holder<E> getHolder() {
			return CastUtil.uncheckedCast(holder);
		}
		
		@Override
		public boolean isPresent() {
			return holder.isBound();
		}
		
		public DeferredHolder<C, E> getRegistryHolder() {
			return holder;
		}
	}
	
	public static class Factory implements CommonRegister.Factory {
		
		@Override
		public <C> CommonRegister<C> create(ResourceKey<? extends Registry<C>> key, String modid) {
			return new NeoForgeCommonRegister<>(key, modid);
		}
	}
}
