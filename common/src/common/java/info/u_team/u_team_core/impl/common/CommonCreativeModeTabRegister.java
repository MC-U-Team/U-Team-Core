package info.u_team.u_team_core.impl.common;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import info.u_team.u_team_core.api.registry.CreativeModeTabRegister;
import info.u_team.u_team_core.api.registry.ResourceEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public abstract class CommonCreativeModeTabRegister implements CreativeModeTabRegister {
	
	private final String modid;
	
	protected final Map<CreativeModeTabEntry, Consumer<CreativeModeTab.Builder>> entries;
	protected final Set<ResourceEntry<CreativeModeTab>> entriesView;
	
	protected CommonCreativeModeTabRegister(String modid) {
		this.modid = modid;
		entries = new LinkedHashMap<>();
		entriesView = Collections.unmodifiableSet(entries.keySet());
	}
	
	@Override
	public ResourceEntry<CreativeModeTab> register(String name, BiConsumer<ResourceLocation, CreativeModeTab.Builder> consumer) {
		return register(name, builder -> consumer.accept(new ResourceLocation(modid, name), builder));
	}
	
	@Override
	public ResourceEntry<CreativeModeTab> register(String name, Consumer<CreativeModeTab.Builder> consumer) {
		final ResourceLocation id = new ResourceLocation(modid, name);
		
		final CreativeModeTabEntry entry = new CreativeModeTabEntry(id);
		if (entries.putIfAbsent(entry, consumer) != null) {
			throw new IllegalArgumentException("Duplicate registration " + name);
		}
		return entry;
	}
	
	@Override
	public String getModid() {
		return modid;
	}
	
	@Override
	public Collection<ResourceEntry<CreativeModeTab>> getEntries() {
		return entriesView;
	}
	
	protected void updateReference(CreativeModeTabEntry entry, CreativeModeTab tab) {
		entry.updateReference(tab);
	}
	
	protected void applyBuilderDefaults(CreativeModeTabEntry entry, CreativeModeTab.Builder builder) {
		final ResourceLocation id = entry.getId();
		builder.title(Component.translatable("creativetabs.%s.%s".formatted(id.getNamespace(), id.getPath())));
	}
	
	public static class CreativeModeTabEntry implements ResourceEntry<CreativeModeTab> {
		
		private final ResourceLocation id;
		private CreativeModeTab value;
		
		CreativeModeTabEntry(ResourceLocation id) {
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
}
