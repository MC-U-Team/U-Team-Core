package info.u_team.u_team_core.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.event.SetupEvents;
import info.u_team.u_team_core.util.CastUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class FabricCommonRegister<C> implements CommonRegister<C> {
	
	private final Registry<C> registry;
	private final String modid;
	
	private final Map<FabricRegistryEntry<C>, Supplier<? extends C>> entries;
	private final Set<RegistryEntry<C>> entriesView;
	
	private boolean registryFrozen;
	
	FabricCommonRegister(ResourceKey<? extends Registry<C>> key, String modid) {
		registry = CastUtil.uncheckedCast(BuiltInRegistries.REGISTRY.get(key.location()));
		this.modid = modid;
		entries = new LinkedHashMap<>();
		entriesView = Collections.unmodifiableSet(entries.keySet());
	}
	
	@Override
	public <E extends C> FabricRegistryEntry<E> register(String name, Function<ResourceLocation, ? extends E> function) {
		return register(name, () -> function.apply(ResourceLocation.fromNamespaceAndPath(modid, name)));
	}
	
	@Override
	public <E extends C> FabricRegistryEntry<E> register(String name, Supplier<? extends E> supplier) {
		if (registryFrozen) {
			throw new IllegalArgumentException("Registry is already frozen");
		}
		
		final ResourceLocation id = ResourceLocation.fromNamespaceAndPath(modid, name);
		final ResourceKey<C> key = ResourceKey.create(registry.key(), id);
		
		final FabricRegistryEntry<E> entry = new FabricRegistryEntry<>(id, CastUtil.uncheckedCast(key));
		if (entries.putIfAbsent(CastUtil.uncheckedCast(entry), supplier) != null) {
			throw new IllegalArgumentException("Duplicate registration " + name);
		}
		return entry;
	}
	
	@Override
	public void register() {
		SetupEvents.REGISTER.register(this::registerEntries);
	}
	
	private void registerEntries(ResourceKey<? extends Registry<?>> eventKey) {
		if (eventKey.equals(registry.key())) {
			registryFrozen = true;
			for (final Entry<FabricRegistryEntry<C>, Supplier<? extends C>> entry : entries.entrySet()) {
				final FabricRegistryEntry<C> registryEntry = entry.getKey();
				Registry.register(registry, registryEntry.getKey(), entry.getValue().get());
				registryEntry.updateReference(registry);
			}
		}
	}
	
	@Override
	public String getModid() {
		return modid;
	}
	
	@Override
	public ResourceKey<? extends Registry<C>> getRegistryKey() {
		return registry.key();
	}
	
	@Override
	public Collection<RegistryEntry<C>> getEntries() {
		return entriesView;
	}
	
	public static class FabricRegistryEntry<E> implements RegistryEntry<E> {
		
		private final ResourceLocation id;
		private final ResourceKey<E> key;
		
		private E value;
		private Holder<E> holder;
		
		FabricRegistryEntry(ResourceLocation id, ResourceKey<E> key) {
			this.id = id;
			this.key = key;
		}
		
		void updateReference(Registry<E> registry) {
			value = registry.get(id);
			holder = registry.getHolder(key).orElse(null);
		}
		
		@Override
		public E get() {
			Objects.requireNonNull(value, () -> "Registry entry not present: " + id);
			return value;
		}
		
		@Override
		public ResourceLocation getId() {
			return id;
		}
		
		@Override
		public ResourceKey<E> getKey() {
			return key;
		}
		
		@Override
		public Optional<Holder<E>> getHolder() {
			return Optional.ofNullable(holder);
		}
		
		@Override
		public boolean isPresent() {
			return value != null;
		}
		
	}
	
	public static class Factory implements CommonRegister.Factory {
		
		@Override
		public <C> CommonRegister<C> create(ResourceKey<? extends Registry<C>> key, String modid) {
			return new FabricCommonRegister<>(key, modid);
		}
		
	}
	
}
