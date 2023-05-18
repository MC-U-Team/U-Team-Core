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
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.CreativeModeTabEvent;

public class ForgeCreativeModeTabRegister implements CreativeModeTabRegister {
	
	private final String modid;
	
	private final Map<ForgeCreativeModeTabEntry, Consumer<CreativeModeTab.Builder>> entries = new LinkedHashMap<>();
	private final Set<ResourceEntry<CreativeModeTab>> entriesView = Collections.unmodifiableSet(entries.keySet());
	
	ForgeCreativeModeTabRegister(String modid) {
		this.modid = modid;
	}
	
	@Override
	public ResourceEntry<CreativeModeTab> register(String name, BiConsumer<ResourceLocation, CreativeModeTab.Builder> consumer) {
		return register(name, builder -> consumer.accept(new ResourceLocation(modid, name), builder));
	}
	
	@Override
	public ResourceEntry<CreativeModeTab> register(String name, Consumer<CreativeModeTab.Builder> consumer) {
		final ResourceLocation id = new ResourceLocation(modid, name);
		
		final ForgeCreativeModeTabEntry entry = new ForgeCreativeModeTabEntry(id);
		if (entries.putIfAbsent(entry, consumer) != null) {
			throw new IllegalArgumentException("Duplicate registration " + name);
		}
		return entry;
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(bus -> bus.addListener(this::registerTab));
	}
	
	private void registerTab(CreativeModeTabEvent.Register event) {
		for (final Entry<ForgeCreativeModeTabEntry, Consumer<CreativeModeTab.Builder>> entry : entries.entrySet()) {
			final ForgeCreativeModeTabEntry registryEntry = entry.getKey();
			final CreativeModeTab tab = event.registerCreativeModeTab(registryEntry.getId(), builder -> {
				builder.title(Component.translatable("creativetabs.%s.%s".formatted(registryEntry.getId().getNamespace(), registryEntry.getId().getPath())));
				entry.getValue().accept(builder);
			});
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
	
	public static class ForgeCreativeModeTabEntry implements ResourceEntry<CreativeModeTab> {
		
		private final ResourceLocation id;
		private CreativeModeTab value;
		
		ForgeCreativeModeTabEntry(ResourceLocation id) {
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
			return new ForgeCreativeModeTabRegister(modid);
		}
	}
}
