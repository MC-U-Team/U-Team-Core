package info.u_team.u_team_core.impl.common;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.LazyEntry;
import info.u_team.u_team_core.api.registry.client.KeyMappingRegister;
import net.minecraft.client.KeyMapping;

public abstract class CommonKeyMappingRegister implements KeyMappingRegister {
	
	protected final Map<KeyMappingLazyEntry, Supplier<KeyMapping>> entries;
	
	protected CommonKeyMappingRegister() {
		entries = new LinkedHashMap<>();
	}
	
	@Override
	public LazyEntry<KeyMapping> register(Supplier<KeyMapping> supplier) {
		final KeyMappingLazyEntry entry = new KeyMappingLazyEntry();
		entries.put(entry, supplier);
		return entry;
	}
	
	protected void updateReference(KeyMappingLazyEntry entry, KeyMapping key) {
		entry.updateReference(key);
	}
	
	public static class KeyMappingLazyEntry implements LazyEntry<KeyMapping> {
		
		private KeyMapping value;
		
		KeyMappingLazyEntry() {
		}
		
		void updateReference(KeyMapping key) {
			value = key;
		}
		
		@Override
		public KeyMapping get() {
			Objects.requireNonNull(value, () -> "Key mapping not present");
			return value;
		}
		
		@Override
		public boolean isPresent() {
			return value != null;
		}
	}
}
