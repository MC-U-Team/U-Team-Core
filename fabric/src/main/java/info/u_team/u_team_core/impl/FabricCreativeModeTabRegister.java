package info.u_team.u_team_core.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import info.u_team.u_team_core.api.registry.CreativeModeTabRegister;
import info.u_team.u_team_core.api.registry.ResourceEntry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public class FabricCreativeModeTabRegister implements CreativeModeTabRegister {
	
	private final String modid;
	
	private final Map<FabricCreativeModeTabEntry, Consumer<CreativeModeTab.Builder>> entries = new LinkedHashMap<>();
	private final Set<ResourceEntry<CreativeModeTab>> entriesView = Collections.unmodifiableSet(entries.keySet());
	
	FabricCreativeModeTabRegister(String modid) {
		this.modid = modid;
	}
	
	@Override
	public ResourceEntry<CreativeModeTab> register(String name, BiConsumer<ResourceLocation, CreativeModeTab.Builder> consumer) {
		return register(name, builder -> consumer.accept(new ResourceLocation(modid, name), builder));
	}
	
	@Override
	public ResourceEntry<CreativeModeTab> register(String name, Consumer<CreativeModeTab.Builder> consumer) {
		final ResourceLocation id = new ResourceLocation(modid, name);
		
		final FabricCreativeModeTabEntry entry = new FabricCreativeModeTabEntry(id);
		if (entries.putIfAbsent(entry, consumer) != null) {
			throw new IllegalArgumentException("Duplicate registration " + name);
		}
		return entry;
	}
	
	@Override
	public void register() {
		for (final Entry<FabricCreativeModeTabEntry, Consumer<CreativeModeTab.Builder>> entry : entries.entrySet()) {
			final FabricCreativeModeTabEntry registryEntry = entry.getKey();
			final CreativeModeTab.Builder builder = FabricItemGroup.builder(registryEntry.getId());
			builder.title(Component.translatable("creativetabs.%s.%s".formatted(registryEntry.getId().getNamespace(), registryEntry.getId().getPath())));
			entry.getValue().accept(builder);
			final CreativeModeTab tab = builder.build();
			registryEntry.updateReference(tab);
		}
	}
	
	@Override
	public String getModid() {
		return modid;
	}
	
	@Override
	public Collection<ResourceEntry<CreativeModeTab>> getEntries() {
		return entriesView;
	}
	
	public static class FabricCreativeModeTabEntry implements ResourceEntry<CreativeModeTab> {
		
		private final ResourceLocation id;
		private CreativeModeTab value;
		
		FabricCreativeModeTabEntry(ResourceLocation id) {
			this.id = id;
		}
		
		void updateReference(CreativeModeTab tab) {
			value = tab;
		}
		
		@Override
		public CreativeModeTab get() {
			Objects.requireNonNull(value, () -> "Creative mode tab not present: " + id);
			return value;
		}
		
		@Override
		public ResourceLocation getId() {
			return id;
		}
		
		@Override
		public boolean isPresent() {
			return value != null;
		}
	}
	
	public static class Factory implements CreativeModeTabRegister.Factory {
		
		@Override
		public CreativeModeTabRegister create(String modid) {
			return new FabricCreativeModeTabRegister(modid);
		}
	}
}
